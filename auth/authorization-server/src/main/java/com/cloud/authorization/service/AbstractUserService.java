package com.cloud.authorization.service;

import com.cloud.common.base.entity.AbstractUser;
import com.cloud.common.repository.AbstractUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public abstract class AbstractUserService<T extends AbstractUser> {

    @Resource
    AbstractUserService abstractUserService;

    @Resource
    AbstractUserRepository abstractUserRepository;

    public Optional<AbstractUser> getUserByPrincipal(Principal principal) {
        return this.abstractUserRepository.findByUuid(principal.getName());
    }
}
