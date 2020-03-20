package com.xy.goone.modules.controller;

import com.xy.goone.modules.service.PlayerService;
import com.xy.goone.modules.service.UserPlayerService;

import javax.annotation.Resource;

public class PlayerController {

    @Resource
    PlayerService playerService;

    @Resource
    UserPlayerService userPlayerService;


/*
    @ApiOperation(value = "获取全部/关注选手列表")
    @RequestMapping(value = "/getPlayerList", method = RequestMethod.POST)
    public Result<Object> getPlayers(@RequestBody Map<String, String> map) {
        int page = Integer.parseInt(map.get("page").trim());
        int size = Integer.parseInt(map.get("size").trim());
        int userId = Integer.valueOf(map.get("userId").trim());
        String type = map.get("type");//0全部 1关注
        //获取关注选手
       // List<Player> lm = playerService.selectPlayers(u.getId());
        Page<UserPlayer> ups =  userPlayerService.findByUserId(userId,1,page,size);
       Page<Player> players = playerService.getFocus(userId);
        if (lm.size()>0){
            page=page+1;//此处page从1开始
            Page pageResult = PageUtil.getPage(page, size, lm);
            return new ResultUtil<>().setData(pageResult.getContent());
        }
        return new ResultUtil<>().setNoData(null);
    }*/
}
