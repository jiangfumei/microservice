package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.PlayerRepository;
import com.xy.goone.modules.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author fumei.jiang
 * @date 2019-08-08 15:44
 */
@Slf4j
@Service
@Transactional
public class PlayerService {

    @Resource
    PlayerRepository playerRepository;

    public Page<Player> getAllPlayerList(int status, int page, int size) {
        return playerRepository.findAllByStatus(status, PageRequest.of(page, size));
    }

}
