package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.TeamRepository;
import com.xy.goone.modules.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TeamService {

    @Resource
    private TeamRepository teamRepository;

    public Page<Team> findByKey(String key, int page, int size) {
        return teamRepository.findByKey(key, PageRequest.of(page, size));
    }
}
