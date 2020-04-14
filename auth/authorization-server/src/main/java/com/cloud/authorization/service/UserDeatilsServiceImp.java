package com.cloud.authorization.service;

import com.cloud.authorization.entity.User;
import com.cloud.authorization.feign.UserFeign;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserDeatilsServiceImp implements UserDetailsService {
    @Resource
    UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (StringUtils.isBlank(userName)){
            //todo: 自定义异常类
            log.error("用户名不能为空");
            //throw new Exception("用户名为空");
        }
        //获取本地用户
        User user = userFeign.getByUsername(userName);
        if(user != null){
            //返回oauth2的用户
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_SYSTEMADMIN")
            ) ;
        }else{
            throw  new UsernameNotFoundException("用户["+userName+"]不存在");
        }
    }
}
