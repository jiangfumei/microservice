package com.cloud.sysadmin.repository;

import com.cloud.common.base.entity.SysRole;
import com.cloud.common.base.entity.SysUser;
import com.cloud.common.base.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Long> , JpaSpecificationExecutor<SysUserRole> {

    public List<SysRole> findBySysUser(SysUser user);
}
