package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.DepartmentHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentHeaderRepository extends JpaRepository<DepartmentHeader,Long> {

    List<DepartmentHeader> findByDepartmentIdAndType(long depId,int type);

    void deleteByDepartmentId(long depId);
}
