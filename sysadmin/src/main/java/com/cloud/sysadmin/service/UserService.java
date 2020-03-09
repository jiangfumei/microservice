package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    List<User> findByDepartmentId(long departmentId);


}
