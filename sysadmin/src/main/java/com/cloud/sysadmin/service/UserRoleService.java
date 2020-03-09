package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.UserRole;

import java.util.List;

public interface UserRoleService {


    List<Role> findRolesByUserId(long id);

    List<String> findDepIdsByUserId(long userId);

    void deleteByUserId(long userId);

    List<UserRole> findByRoleId(long roleId);
}
