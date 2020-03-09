package com.cloud.sysadmin.service;

import java.util.List;

public interface DepartmentHeaderService {

    List<Long> findHeaderByDepartmentId(long departmentId, Integer type);

    void deleteByDepartmentId(long depId);

    void deleteByUserId(long userId);

}
