package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.VideoOrderTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author fumei.jiang
 * @date 2019-08-12 13:10
 */
public interface VideoOrderTaskRepository extends JpaRepository<VideoOrderTask, Integer> {

    @Query("select vo from VideoOrderTask vo where vo.user.id=?1 and vo.status =1 order by vo.orderTime desc ")
    Page<VideoOrderTask> findByUserIdAndStatus(int userId, Date now, Pageable pageable);

    @Query("select vo from VideoOrderTask vo where vo.orderTime >= ?1 and  vo.orderTime <= ?2 and vo.status=?3")
    List<VideoOrderTask> findByDateAndStatus(Date start, Date end, int status);

    @Query("select vo from VideoOrderTask vo where vo.user.id=?1 and vo.video.id = ?2")
    VideoOrderTask findVideoTaskIsOrNotExist(int userId, int videoId);

    @Query("select vo from VideoOrderTask vo where vo.user.id =?1 and vo.status=?2")
    List<VideoOrderTask> findByUserAndStatus(int userId, int status, Date now);

    List<VideoOrderTask> findByUserIdAndStatus(int userId,int status);


}
