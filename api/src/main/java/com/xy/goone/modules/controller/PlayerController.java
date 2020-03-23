package com.xy.goone.modules.controller;

import com.xy.goone.modules.service.PlayerService;
import com.xy.goone.modules.service.UserPlayerService;

import javax.annotation.Resource;

public class PlayerController {

    @Resource
    PlayerService playerService;

    @Resource
    UserPlayerService userPlayerService;

}
