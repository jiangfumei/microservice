package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.UserRepository;
import com.xy.goone.modules.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

/**
 * @author fumei.jiang
 * @date 2019-08-08 11:05
 */
@Service
@Transactional
@Slf4j
public class UserService {

    @Resource
    EntityManager manager;

    @Resource
    UserRepository userRepository;

    public void updateUser(User user) {
        manager.merge(user);

    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }


    public User findByEmailAndStatus(String email, int status) {
        return userRepository.findByEmailAndStatus(email, status);
    }

}
