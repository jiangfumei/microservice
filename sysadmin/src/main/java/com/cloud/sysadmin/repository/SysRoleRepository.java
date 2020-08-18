package com.cloud.sysadmin.repository;

import com.cloud.common.base.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysRoleRepository extends JpaRepository<SysRole,Long>, JpaSpecificationExecutor<SysRole> {

}
