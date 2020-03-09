package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> findByTitle(String title);

    List<Permission> findByTypeAndStatusOrderBySortOrder(int type,int status);

    Permission update(Permission permission);

    List<Permission> findByLevelOrderBySortOrder(int level);

    List<Permission> findByParentIdOrderBySortOrder(long parentId);

    List<Permission> findByTitleLikeOrderBySortOrder(String title);

    List<Permission> findByUserId(long userId);
}
