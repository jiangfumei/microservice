package com.cloud.sysadmin.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api("用户接口")
@RequestMapping(value = "/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String getString(){
        return "hello";
    }

    //项目开发工作流程的合理化、项目开发时间和成本预算控制、项目风险控制、项目工作安排效率、开发工作的协调管理过程、工程化开发方式的运用、程序的运行效率和统一的运行标准、信息系统需求方满意度
}
