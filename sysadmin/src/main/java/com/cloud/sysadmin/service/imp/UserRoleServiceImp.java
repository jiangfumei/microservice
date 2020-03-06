package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.repository.UserRoleRepository;
import com.cloud.sysadmin.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserRoleServiceImp implements UserRoleService {

    @Resource
    UserRoleRepository userRoleRepository;

    public List<Role> findRolesByUserId(long id){
        return userRoleRepository.findByUserId(id);
    }

    @Override
    public List<String> findDepIdsByUserId(long userId) {
        return null;
    }
}
