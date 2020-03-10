package com.cloud.sysadmin.controller;

import com.cloud.common.base.vo.Result;
import com.cloud.common.util.PageUtil;
import com.cloud.common.util.ResultUtil;
import com.cloud.common.vo.PageVo;
import com.cloud.sysadmin.entity.Dict;
import com.cloud.sysadmin.entity.DictData;
import com.cloud.sysadmin.repository.DictDataRepository;
import com.cloud.sysadmin.repository.DictRepository;
import com.cloud.sysadmin.service.DictDataService;
import com.cloud.sysadmin.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(value = "字典数据管理接口")
@RequestMapping("/dictData")
@CacheConfig(cacheNames = "dictData")
@Transactional
public class DictDataController {

    @Resource
    DictDataService dictDataService;

    @Resource
    DictService dictService;

    @Resource
    DictRepository dictRepository;

    @Resource
    DictDataRepository dictDataRepository;

    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/getByCondition",method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<Page<DictData>> getByCondition(DictData dictData,
                                                 PageVo pageVo){

        Page<DictData> page = dictDataService.findByCondition(dictData, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<DictData>>().setData(page);
    }

    @RequestMapping(value = "/getByType/{type}",method = RequestMethod.GET)
    @ApiOperation(value = "通过类型获取")
    @Cacheable(key = "#type")
    public Result<Object> getByType(@PathVariable String type){

        Dict dict = dictService.findByType(type);
        if (dict == null) {
            return ResultUtil.error("字典类型 "+ type +" 不存在");
        }
        List<DictData> list = dictDataService.findByDictId(dict.getId());
        return ResultUtil.data(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(DictData dictData){

        Dict dict = dictRepository.getOne(dictData.getDictId());
        if (dict == null) {
            return ResultUtil.error("字典类型id不存在");
        }
        dictDataRepository.save(dictData);
        // 删除缓存
        redisTemplate.delete("dictData::"+dict.getType());
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(DictData dictData){

        dictDataService.update(dictData);
        // 删除缓存
        Dict dict = dictRepository.getOne(dictData.getDictId());
        redisTemplate.delete("dictData::"+dict.getType());
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable long[] ids){

        for(long id : ids){
            DictData dictData = dictDataRepository.getOne(id);
            Dict dict = dictRepository.getOne(dictData.getDictId());
            dictDataService.deleteByDictId(id);
            // 删除缓存
            redisTemplate.delete("dictData::"+dict.getType());
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
