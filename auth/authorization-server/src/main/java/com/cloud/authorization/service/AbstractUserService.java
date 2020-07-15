package com.cloud.authorization.service;

import com.cloud.authorization.entity.AbstractUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public abstract class AbstractUserService<T extends AbstractUser> {

/*    @Resource
    Abs

    public Optional<AbstractUser> getUserByPrincipal(Principal principal) {
        return this.usersRepository.findByUuid(principal.getName());
    }*/
}
