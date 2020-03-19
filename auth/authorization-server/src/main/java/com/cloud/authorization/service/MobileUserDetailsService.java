package com.cloud.authorization.service;

import com.cloud.authorization.config.SecurityUserDetails;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {

   @Resource
   private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String phone) {
        User user = userService.findByPhone(phone);
        return new SecurityUserDetails(user);
    }

}
