package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author fumei.jiang
 * @date 2019-08-08 14:00
 */
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("select v from Video v where v.id=?1 and v.status = 1")
    Video getVideoDetail(int id);

    @Query("select v from Video v where v.team.id=?1 and v.status=1")
    Page<Video> findByTeam(int teamId, Pageable pageable);

    @Query("select v from Video v where v.liveTime>=?1 and v.liveTime<=?2 and v.status=?3 order by v.liveTime DESC")
    Page<Video> findByDate(Date start, Date end, int status, Pageable pageable);

    @Query("select v from Video v where v.title like %?1% and v.status = 1")
    Page<Video> findByKey(String key, Pageable pageable);

    @Query("select v from Video v where v.isLive=?1 and v.status=?2")
    Page<Video> findByLiving(int status, int a, Pageable pageable);

    @Query("select v from Video v where v.liveTime between ?1 and ?2 and v.status=?3")
    List<Video> findByToday(Date start, Date end, int status);

    @Query("select v from Video v where v.liveTime>=?1 and v.liveTime<=?2 and v.status=?3")
    List<Video> findByDateAndStatus(Date start, Date end, int status);

}
