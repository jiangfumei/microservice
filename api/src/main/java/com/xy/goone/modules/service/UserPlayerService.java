package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.UserPlayerRepository;
import com.xy.goone.modules.domain.UserPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
@Transactional
public class UserPlayerService {
    @Resource
    UserPlayerRepository userPlayerRepository;
    @Resource
    EntityManager entityManager;

    public Page<UserPlayer> findByUserId(int userId,int status,int page,int size){
        return userPlayerRepository.findByUserIdAndStatus(userId, status,PageRequest.of(page, size));
    }


}
