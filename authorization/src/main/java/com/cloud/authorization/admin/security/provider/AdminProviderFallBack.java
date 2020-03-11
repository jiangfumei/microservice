package com.cloud.authorization.admin.security.provider;

import com.cloud.sysadmin.entity.Permission;
import com.cloud.sysadmin.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminProviderFallBack implements AdminProvider {


    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(int type, int status) {
        return new ArrayList<Permission>();
    }

    @Override
    public User getByUsername(String name) {
        return new User();
    }
}
