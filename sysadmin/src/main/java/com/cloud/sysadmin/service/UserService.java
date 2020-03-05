package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.User;

public interface UserService {

    User findUserByUsername(String username);
}
