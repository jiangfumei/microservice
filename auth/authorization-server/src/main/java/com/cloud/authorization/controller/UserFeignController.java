package com.cloud.authorization.controller;

import com.cloud.authorization.entity.User;
import com.cloud.authorization.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserFeignController {

    @Resource
    UserFeign userFeign;

    @RequestMapping(value = "/getUser" , method = RequestMethod.POST)
    public User search(@RequestParam("username") String username){
        return userFeign.getByUsername(username);
    }
}
