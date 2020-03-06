package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findByParentIdOrderBySortOrder(long parentId,boolean openDataFilter);

    Department findByParentId(long parentId);

    List<Department> findByParentIdAndStatusOrderBySortOrder(long parentId,int status);

    void update(Department department);

    Department merge(Department department);

}
