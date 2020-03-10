package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DictRepository extends JpaRepository<Dict,Long>, JpaSpecificationExecutor<Dict> {

    Dict findByType(String type);

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    @Query(value = "select * from dict d where d.title like %:key% or d.type like %:key% order by d.sort_order", nativeQuery = true)
    List<Dict> findByTitleOrTypeLike(String key);

    /*@Query(value = "select * from dict d order by d.sort_order", nativeQuery = true)*/
    List<Dict> findAllBySortOrder();
}
