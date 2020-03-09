package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.Permission;
import com.cloud.sysadmin.repository.PermissionRepository;
import com.cloud.sysadmin.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PermissionServiceImp implements PermissionService {

    @Resource
    PermissionRepository permissionRepository;

    @Resource
    EntityManager manager;

    @Override
    public List<Permission> findByTitle(String title) {
        return permissionRepository.findByTitle(title);
    }

    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(int type, int status) {
        return permissionRepository.findByTypeAndStatusOrderBySortOrder(type,status);
    }

    @Override
    public Permission update(Permission permission) {
        return manager.merge(permission);
    }

    @Override
    public List<Permission> findByLevelOrderBySortOrder(int level) {
        return permissionRepository.findByLevelOrderBySortOrder(level);
    }

    @Override
    public List<Permission> findByParentIdOrderBySortOrder(long parentId) {
        return permissionRepository.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<Permission> findByTitleLikeOrderBySortOrder(String title) {
        return permissionRepository.findByTitleLikeOrderBySortOrder(title);
    }

    @Override
    public List<Permission> findByUserId(long userId) {
        return permissionRepository.findByUserId(userId);
    }
}
