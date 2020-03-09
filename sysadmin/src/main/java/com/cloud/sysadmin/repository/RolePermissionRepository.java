package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> , JpaSpecificationExecutor<RolePermission> {

    void deleteByRoleId(long roleId);

    List<RolePermission> findByRoleId(long roleId);
}
