package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
