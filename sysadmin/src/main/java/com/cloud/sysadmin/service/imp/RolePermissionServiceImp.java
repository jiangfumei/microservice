package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.RolePermission;
import com.cloud.sysadmin.repository.RolePermissionRepository;
import com.cloud.sysadmin.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional
public class RolePermissionServiceImp implements RolePermissionService {

    @Resource
    RolePermissionRepository rolePermissionRepository;

    @Override
    public void deleteByRoleId(long roleId) {
        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    public List<RolePermission> findByRoleId(long roleId) {
        return rolePermissionRepository.findByRoleId(roleId);
    }

    @Override
    public List<RolePermission> findByPermissionId(long permissionId) {
        return rolePermissionRepository.findByPermissionId(permissionId);
    }
}
