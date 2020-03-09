package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.RoleDepartment;

import java.util.List;

public interface RoleDepartmentService {

    void deleteByDepartmentId(long depId);

    void deleteByRoleId(long roleId);

    List<RoleDepartment> findByRoleId(long roleId);

}
