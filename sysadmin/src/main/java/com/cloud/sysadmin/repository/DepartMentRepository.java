package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartMentRepository extends JpaRepository<Department,Long>, JpaSpecificationExecutor<Department> {


}
