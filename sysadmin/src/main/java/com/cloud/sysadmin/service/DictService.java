package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.Dict;

import java.util.List;

public interface DictService {

    Dict findByType(String type);

    void update(Dict dict);

    List<Dict> findByTitleOrTypeLike(String key);
}
