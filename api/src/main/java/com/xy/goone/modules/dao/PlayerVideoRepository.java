package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.PlayerVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fumei.jiang
 * @date 2019-08-20 14:00
 */
public interface PlayerVideoRepository extends JpaRepository<PlayerVideo, Integer> {

    List<PlayerVideo> findByPlayerId(int id);

    @Query("select pv from PlayerVideo  pv where pv.videoId=?1")
    List<PlayerVideo> findByVideoId(int id);

}
