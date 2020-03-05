package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.repository.UserRepository;
import com.cloud.sysadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Slf4j
@Service
@Transactional
public class UserServiceImp implements UserService {

    @Resource
    UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
