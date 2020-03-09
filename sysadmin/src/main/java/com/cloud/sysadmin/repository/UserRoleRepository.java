package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Long>, JpaSpecificationExecutor<UserRole> {

    List<Role> findByUserId(long userId);

    void deleteByUserId(long userId);

    List<UserRole> findByRoleId(long roleId);

}
