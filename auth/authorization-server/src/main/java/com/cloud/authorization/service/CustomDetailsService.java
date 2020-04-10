package com.cloud.authorization.service;

import com.cloud.authorization.entity.User;
import com.cloud.authorization.feign.UserFeign;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Resource
    UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userFeign.getByUsername(username);
        return (UserDetails) user;
    }


}
