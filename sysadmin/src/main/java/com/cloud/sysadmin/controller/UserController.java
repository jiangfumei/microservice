package com.cloud.sysadmin.controller;

import com.cloud.common.base.vo.Result;
import com.cloud.common.util.ResultUtil;
import com.cloud.sysadmin.entity.User;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api("user")
@RequestMapping(value = "/user")
public class UserController {

    public Result<Object> register(User user){
        return new ResultUtil<>().setData(user);
    }

}
