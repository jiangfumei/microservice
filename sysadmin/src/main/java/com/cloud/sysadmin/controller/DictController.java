package com.cloud.sysadmin.controller;

import com.cloud.common.base.vo.Result;
import com.cloud.common.util.ResultUtil;
import com.cloud.sysadmin.entity.Dict;
import com.cloud.sysadmin.repository.DictRepository;
import com.cloud.sysadmin.service.DictDataService;
import com.cloud.sysadmin.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Transactional
@Api("字典接口")
@CacheConfig(cacheNames = "dict")
@RequestMapping(value = "/dict")
public class DictController {

    @Resource
    DictService dictService;

    @Resource
    DictRepository dictRepository;

    @Autowired
    private DictDataService dictDataService;


    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<Dict>> getAll(){

        /*List<Dict> list = dictService.findAllOrderBySortOrder();

        return new ResultUtil<List<Dict>>().setData(list);*/
        return null;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(Dict dict){

        if(dictService.findByType(dict.getType())!=null){
            return ResultUtil.error("字典类型Type已存在");
        }
        dictRepository.save(dict);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(Dict dict){

        Dict old = dictRepository.getOne(dict.getId());
        // 若type修改判断唯一
        if(!old.getType().equals(dict.getType())&&dictService.findByType(dict.getType())!=null){
            return ResultUtil.error("字典类型Type已存在");
        }
        dictService.update(dict);
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "通过id删除")
    public Result<Object> delAllByIds(@PathVariable long id){


        Dict dict = dictRepository.getOne(id);
        dictRepository.deleteById(id);
        //dictDataService.deleteByDictId(id);
        // 删除缓存
        redisTemplate.delete("dictData::"+dict.getType());
        return ResultUtil.success("删除成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "搜索字典")
    public Result<List<Dict>> searchPermissionList(@RequestParam String key){

        List<Dict> list = dictService.findByTitleOrTypeLike(key);
        return new ResultUtil<List<Dict>>().setData(list);
    }

}
