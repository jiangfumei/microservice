package com.cloud.authorization.service;

import com.cloud.common.base.entity.AbstractUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract class AbstractUserService<T extends AbstractUser> {

}
