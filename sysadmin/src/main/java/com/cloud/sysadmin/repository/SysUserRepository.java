package com.cloud.sysadmin.repository;

import com.cloud.common.base.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SysUserRepository extends JpaRepository<SysUser,Long>, JpaSpecificationExecutor<SysUser> {

    Optional<SysUser> findByUsername(String username);

    Optional<SysUser> findById(long id);

}
