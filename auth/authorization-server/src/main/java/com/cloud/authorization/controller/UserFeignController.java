package com.cloud.authorization.controller;

import com.cloud.authorization.entity.UserEntity;
import com.cloud.authorization.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@Slf4j
public class UserFeignController {

    @Resource
    UserFeign userFeign;

    @RequestMapping(value = "/getUser" , method = RequestMethod.POST)
    public UserEntity search(@PathVariable(value = "username") String username){
        return userFeign.getByUsername(username);
    }

    /**
     * 获取授权的用户信息
     * @param principal 当前用户
     * @return 授权信息
     */
    @GetMapping("/user")
    public Principal user(Principal principal){
        return principal;
    }
}
