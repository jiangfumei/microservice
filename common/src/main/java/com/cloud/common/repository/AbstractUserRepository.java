package com.cloud.common.repository;

import com.cloud.common.base.entity.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbstractUserRepository extends JpaRepository<AbstractUser,Long> {

    public Optional<AbstractUser> findByUuid(String uuid);
}
