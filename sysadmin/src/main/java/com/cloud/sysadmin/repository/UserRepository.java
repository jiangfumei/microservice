package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findUserByUsername(String name);

    List<User> findByDepartmentId(long depId);
}
