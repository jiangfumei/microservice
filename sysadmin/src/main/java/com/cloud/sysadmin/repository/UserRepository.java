package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u from User u where u.username=?1")
    User findUserByUsername(String name);

    List<User> findByDepartmentId(long depId);

    /*User findByUsername(String username);*/

    User findByPhone(String phone);
}
