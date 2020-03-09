package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.repository.RoleRepository;
import com.cloud.sysadmin.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Transactional
@Service
public class RoleServiceImp implements RoleService {

    @Resource
    RoleRepository roleRepository;

    @Resource
    EntityManager manager;

    @Override
    public List<Role> findByDefaultRole(Boolean defaultRole) {
        return roleRepository.findByDefaultRole(defaultRole);
    }

    @Override
    public Role update(Role role) {
        return manager.merge(role);
    }
}
