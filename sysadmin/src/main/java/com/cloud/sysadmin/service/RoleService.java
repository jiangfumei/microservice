package com.cloud.sysadmin.service;

import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.entity.Role;

import java.util.List;

public interface RoleService {

    //List<Role> findByDefaultRole(Boolean defaultRole);

    Role update(Role role);

    List<Role> findByCondition(SearchVo searchVo);
}
