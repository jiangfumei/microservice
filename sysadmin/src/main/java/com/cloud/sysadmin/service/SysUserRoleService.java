package com.cloud.sysadmin.service;

import com.cloud.common.base.entity.SysRole;
import com.cloud.common.base.entity.SysUser;
import com.cloud.sysadmin.repository.SysUserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Slf4j
@Service
public class SysUserRoleService {

    @Resource
    SysUserRoleRepository userRoleRepository;

    public List<SysRole> findBySysUser(SysUser user) {
        return userRoleRepository.findBySysUser(user);
    }
}
