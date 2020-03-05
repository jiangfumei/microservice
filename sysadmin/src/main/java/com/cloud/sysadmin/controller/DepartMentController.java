package com.cloud.sysadmin.controller;

import com.cloud.common.base.vo.Result;
import com.cloud.common.util.ResultUtil;
import com.cloud.sysadmin.entity.Department;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.service.DepartmentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Api("部门接口")
@RequestMapping(value = "/department")
public class DepartMentController {

    @Resource
    DepartmentService departmentService;

    @Resource
    StringRedisTemplate redisTemplate;

    /*@RequestMapping(value = "/getByParentId/{parentId}",method = RequestMethod.POST)
    @ApiOperation(value = "通过id获取")
    public Result<List<Department>> getByParentId(@PathVariable String parentId,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

        List<Department> list = new ArrayList<>();
        User u = securityUtil.getCurrUser();
        String key = "department::"+parentId+":"+u.getId()+"_"+openDataFilter;
        String v = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotBlank(v)){
            list = new Gson().fromJson(v, new TypeToken<List<Department>>(){}.getType());
            return new ResultUtil<List<Department>>().setData(list);
        }
        list = departmentService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
        list = setInfo(list);
        redisTemplate.opsForValue().set(key,
                new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<Department>>().setData(list);
    }*/

}
