package com.cloud.authorization.controller;

import com.cloud.authorization.entity.User;
import com.cloud.authorization.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserFeignController {

    @Resource
    UserFeign userFeign;

    @RequestMapping(value = "/getUser" , method = RequestMethod.POST)
    public User search(@PathVariable(value = "username") String username){
        return userFeign.getByUsername(username);
    }
}
