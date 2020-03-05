package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {

    List<Role> findByDefaultRole(Boolean defaultRole);
}
