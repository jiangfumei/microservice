package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.RoleDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDepartmentRepository extends JpaRepository<RoleDepartment,Long>, JpaSpecificationExecutor<RoleDepartment> {

    void deleteByDepartmentId(long depId);
}
