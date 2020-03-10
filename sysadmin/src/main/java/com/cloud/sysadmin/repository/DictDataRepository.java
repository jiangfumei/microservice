package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.DictData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DictDataRepository extends JpaRepository<DictData,Long>, JpaSpecificationExecutor<DictData> {

    void deleteByDictId(long dictId);

    List<DictData> findByDictId(long DictId);
}
