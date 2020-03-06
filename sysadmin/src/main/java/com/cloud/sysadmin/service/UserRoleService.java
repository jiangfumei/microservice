package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.Role;

import java.util.List;

public interface UserRoleService {


    List<Role> findRolesByUserId(long id);

    List<String> findDepIdsByUserId(long userId);
}
