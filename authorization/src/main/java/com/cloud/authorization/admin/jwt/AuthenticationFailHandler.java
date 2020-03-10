package com.cloud.authorization.admin.jwt;


import com.cloud.authorization.admin.security.propertie.SysAdminProperties;
import com.cloud.common.exception.LoginFailLimitException;
import com.cloud.common.util.ReponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SysAdminProperties tokenProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            recordLoginTime(username);
            String key = "loginTimeLimit:"+username;
            String value = (String) redisTemplate.opsForValue().get(key);
            if(StringUtils.isBlank(value)){
                value = "0";
            }
            //获取已登录错误次数
            int loginFailTime = Integer.parseInt(value);
            int restLoginTime = tokenProperties.getLoginTimeLimit() - loginFailTime;
            log.info("用户"+username+"登录失败，还有"+restLoginTime+"次机会");
            if(restLoginTime<=3&&restLoginTime>0){
                ReponseUtil.out(response, ReponseUtil.resultMap(false,500,"用户名或密码错误，还有"+restLoginTime+"次尝试机会"));
            } else if(restLoginTime<=0) {
                ReponseUtil.out(response, ReponseUtil.resultMap(false,500,"登录错误次数超过限制，请"+tokenProperties.getLoginAfterTime()+"分钟后再试"));
            } else {
                ReponseUtil.out(response, ReponseUtil.resultMap(false,500,"用户名或密码错误"));
            }
        } else if (e instanceof DisabledException) {
            ReponseUtil.out(response, ReponseUtil.resultMap(false,500,"账户被禁用，请联系管理员"));
        } else if (e instanceof LoginFailLimitException){
            ReponseUtil.out(response, ReponseUtil.resultMap(false,500,((LoginFailLimitException) e).getMsg()));
        } else {
            ReponseUtil.out(response, ReponseUtil.resultMap(false,500,"登录失败，其他内部错误"));
        }
    }

    /**
     * 判断用户登陆错误次数
     */
    public boolean recordLoginTime(String username){

        String key = "loginTimeLimit:"+username;
        String flagKey = "loginFailFlag:"+username;
        String value = (String) redisTemplate.opsForValue().get(key);
        if(StringUtils.isBlank(value)){
            value = "0";
        }
        //获取已登录错误次数
        int loginFailTime = Integer.parseInt(value) + 1;
        redisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), tokenProperties.getLoginAfterTime(), TimeUnit.MINUTES);
        if(loginFailTime>=tokenProperties.getLoginTimeLimit()){
            redisTemplate.opsForValue().set(flagKey, "fail", tokenProperties.getLoginAfterTime(), TimeUnit.MINUTES);
            return false;
        }
        return true;
    }

}
