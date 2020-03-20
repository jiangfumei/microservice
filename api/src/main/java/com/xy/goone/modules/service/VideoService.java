package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.VideoRepository;
import com.xy.goone.modules.domain.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author fumei.jiang
 * @date 2019-08-08 14:00
 */
@Service
@Slf4j
@Transactional
public class VideoService {

    @Resource
    VideoRepository videoRepository;

    public Page<Video> findByLiving(int status, int a, int page, int size) {
        return videoRepository.findByLiving(status, a, PageRequest.of(page, size));
    }

}
