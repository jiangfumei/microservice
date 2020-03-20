package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fumei.jiang
 * @date 2019-08-08 14:50
 */
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("select t from Team t where t.name like %?1% and t.status = 1")
    Page<Team> findByKey(String key, Pageable pageable);

    Page<Team> findByStatus(int status, Pageable pageable);


}
