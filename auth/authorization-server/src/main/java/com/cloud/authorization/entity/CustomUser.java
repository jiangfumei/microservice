package com.cloud.authorization.entity;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
    public CustomUser(UserEntity user) {
        super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
    }

}
