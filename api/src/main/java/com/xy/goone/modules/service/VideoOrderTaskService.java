package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.VideoOrderTaskRepository;
import com.xy.goone.modules.domain.VideoOrderTask;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhuhai.mao
 * @date 2019-08-08
 */
@Service
@Transactional
public class VideoOrderTaskService {

    @Resource
    VideoOrderTaskRepository videoOrderTaskRepository;

    public List<VideoOrderTask> findByUserId(int userId,int status){
        return videoOrderTaskRepository.findByUserIdAndStatus(userId, status);
    }


}
