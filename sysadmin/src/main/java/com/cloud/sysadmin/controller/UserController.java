package com.cloud.sysadmin.controller;

import com.cloud.sysadmin.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api("用户接口")
@RequestMapping(value = "/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String getString(){
        return "hello";
    }



}
