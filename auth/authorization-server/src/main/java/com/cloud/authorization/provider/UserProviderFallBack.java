package com.cloud.authorization.provider;


import com.cloud.authorization.entity.Permission;

import com.cloud.authorization.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserProviderFallBack implements UserProvider {


    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(int type, int status) {
        return new ArrayList<Permission>();
    }

    @Override
    public User getByUsername(String name) {
        return new User();
    }

    @Override
    public User getByPhone(String phone) {
        return new User();
    }
}
