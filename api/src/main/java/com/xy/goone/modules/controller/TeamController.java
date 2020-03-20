package com.xy.goone.modules.controller;

import com.cloud.common.base.vo.Result;
import com.xy.goone.modules.dao.TeamRepository;
import com.xy.goone.modules.domain.Video;
import com.xy.goone.modules.domain.VideoOrderTask;
import com.xy.goone.modules.service.VideoOrderTaskService;
import com.xy.goone.modules.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @author fumei.jiang
 * @date 2019-08-21 17:21
 */
@Slf4j
@RestController
@RequestMapping(value = "/video")
public class TeamController {

    @Resource
    TeamRepository teamRepository;

    @Resource
    VideoService videoService;

    @Resource
    VideoOrderTaskService videoOrderTaskService;


    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/getVideosByTeam", method = RequestMethod.POST)
    @ApiOperation(value = "获取分类视频列表")
    public Result<Object> getTeams(@RequestBody Map<String, String> map) {
        int page = Integer.valueOf(map.get("page").trim());
        int size = Integer.valueOf(map.get("size").trim());
        int userId = Integer.valueOf(map.get("userId").trim());
        Page<Video> videoPage = videoService.findByLiving(1, 1, page, size);//直播中的视频
        List<VideoOrderTask> vos = videoOrderTaskService.findByUserId(userId,1);

       return null;
    }

}
