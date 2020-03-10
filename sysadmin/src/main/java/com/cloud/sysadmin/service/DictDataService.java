package com.cloud.sysadmin.service;

import com.cloud.sysadmin.entity.DictData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DictDataService {

    void deleteByDictId(long dictId);

    List<DictData> findByDictId(long dictId);

    void update(DictData dictData);

    Page<DictData> findByCondition(DictData dictData, Pageable pageable);
}
