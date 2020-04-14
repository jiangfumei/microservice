package com.cloud.authorization.service;

import com.cloud.authorization.entity.User;
import com.cloud.authorization.feign.UserFeign;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDeatilsServiceImp implements UserDetailsService {
    @Resource
    UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
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
