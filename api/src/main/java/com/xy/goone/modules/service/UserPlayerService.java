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

   /* public List<UserPlayer> findByUserId(int userId,int status){
        return userPlayerRepository.findByUserId(userId,status);
    }

    public UserPlayer findByUserAndPlayer(int userId,int playerId){
        return userPlayerRepository.findByUserIdAndPlayer(userId,playerId);
    }

    public UserPlayer findByUserIdAndPlayerId(int userId,int playerId,int status){
        return userPlayerRepository.findByUserIdAndPlayerId(userId,playerId,status);
    }
    public void updateUserPlayer(UserPlayer userPlayer){
        entityManager.merge(userPlayer);
    }

    public List<UserPlayer> findByUserAndStatus(int userId,int status){
        return userPlayerRepository.findByUserIdAndStatus(userId,status);
    }*/

    public Page<UserPlayer> findByUserId(int userId,int status,int page,int size){
        return userPlayerRepository.findByUserIdAndStatus(userId, status,PageRequest.of(page, size));
    }


}
