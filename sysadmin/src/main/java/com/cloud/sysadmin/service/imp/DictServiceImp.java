package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.Dict;
import com.cloud.sysadmin.repository.DictRepository;
import com.cloud.sysadmin.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
@Transactional
public class DictServiceImp implements DictService {

    @Resource
    DictRepository dictRepository;

    @Resource
    EntityManager manager;

    @Override
    public Dict findByType(String type) {
        return dictRepository.findByType(type);
    }

    @Override
    public void update(Dict dict) {
        manager.merge(dict);
    }

    @Override
    public List<Dict> findByTitleOrTypeLike(String key) {
        return dictRepository.findByTitleOrTypeLike(key);
    }

    @Override
    public List<Dict> findAllBySortOrder() {
        return dictRepository.findAllBySortOrder();
    }
}
