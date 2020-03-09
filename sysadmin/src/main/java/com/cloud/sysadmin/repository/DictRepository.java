package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DictRepository extends JpaRepository<Dict,Long>, JpaSpecificationExecutor<Dict> {

    Dict findByType(String type);

    List<Dict> findByTitleOrTypeLike(String key);
}
