package com.cloud.authorization.service;

import com.cloud.authorization.admin.security.SecurityUserDetails;
import com.cloud.common.exception.LoginFailLimitException;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Service
public class UserDetailService implements UserDetailsService {

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String flagKey = "loginFailFlag:"+s;
        String value = (String) redisTemplate.opsForValue().get(flagKey);
        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        if(StringUtils.isNotBlank(value)){
            //超过限制次数
            throw new LoginFailLimitException("登录错误次数超过限制，请"+timeRest+"分钟后再试");
        }
        User user = userService.findByUsername(s);
        return new SecurityUserDetails(user);
    }

}
