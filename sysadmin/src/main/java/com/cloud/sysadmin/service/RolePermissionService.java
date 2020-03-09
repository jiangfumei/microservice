package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.RolePermission;

import java.util.List;

public interface RolePermissionService {

    void deleteByRoleId(long roleId);

    List<RolePermission> findByRoleId(long roleId);
}
