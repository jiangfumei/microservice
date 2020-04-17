/*
package com.cloud.sysadmin.controller;

import com.cloud.common.base.admin.AdminConstant;
import com.cloud.common.base.vo.Result;
import com.cloud.common.util.CommonUtil;
import com.cloud.common.util.ResultUtil;
import com.cloud.sysadmin.entity.Department;
import com.cloud.sysadmin.entity.DepartmentHeader;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.repository.DepartMentRepository;
import com.cloud.sysadmin.repository.DepartmentHeaderRepository;
import com.cloud.sysadmin.service.DepartmentHeaderService;
import com.cloud.sysadmin.service.DepartmentService;
import com.cloud.sysadmin.service.RoleDepartmentService;
import com.cloud.sysadmin.service.UserService;
import com.cloud.sysadmin.util.HibernateProxyTypeAdapter;
import com.cloud.sysadmin.util.SecurityUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Api("部门接口")
@RequestMapping(value = "/department")
@CacheConfig(cacheNames = "department")
public class DepartMentController {

    @Resource
    DepartmentService departmentService;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SecurityUtil securityUtil;

    @Resource
    DepartmentHeaderService departmentHeaderService;

    @Resource
    DepartMentRepository departMentRepository;

    @Resource
    DepartmentHeaderRepository departmentHeaderRepository;

    @Resource
    RoleDepartmentService roleDepartmentService;

    @Resource
    UserService userService;

    @RequestMapping(value = "/getByParentId/{parentId}",method = RequestMethod.POST)
    @ApiOperation(value = "通过id获取")
    public Result<List<Department>> getByParentId(@PathVariable long parentId,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

        List<Department> list = new ArrayList<>();
        User u = securityUtil.getCurrUser();
        String key = "department::"+parentId+":"+u.getId()+"_"+openDataFilter;
        String v = (String) redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotBlank(v)){
            list = new Gson().fromJson(v, new TypeToken<List<Department>>(){}.getType());
            return new ResultUtil<List<Department>>().setData(list);
        }
        list = departmentService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
        list = setInfo(list);
        redisTemplate.opsForValue().set(key,
                new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<Department>>().setData(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(Department department){

        Department d = departMentRepository.save(department);
        // 同步该节点缓存
        User u = securityUtil.getCurrUser();
        Set<String> keys = redisTemplate.keys("department::"+department.getParentId()+":*");
        redisTemplate.delete(keys);
        // 如果不是添加的一级 判断设置上级为父节点标识
        if(!(AdminConstant.PARENT_ID ==(department.getParentId()))){
            Department parent = departmentService.findByParentId(department.getParentId());
            if(parent.getIsParent()==null||!parent.getIsParent()){
                parent.setIsParent(true);
                departmentService.update(parent);
                // 更新上级节点的缓存
                Set<String> keysParent = redisTemplate.keys("department::"+parent.getParentId()+":*");
                redisTemplate.delete(keysParent);
            }
        }
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(Department department,
                               @RequestParam(required = false) String[] mainHeader,
                               @RequestParam(required = false) String[] viceHeader){

        Department d = departmentService.merge(department);
        // 先删除原数据
        departmentHeaderService.deleteByDepartmentId(department.getId());
        for(String id:mainHeader){
            DepartmentHeader dh = new DepartmentHeader();
            dh.setUserId(Long.valueOf(id));
            dh.setDepartmentId(d.getId());
            dh.setType(AdminConstant.HEADER_TYPE_MAIN);
            departmentHeaderRepository.save(dh);
        }
        for(String id:viceHeader){
            DepartmentHeader dh = new DepartmentHeader();
            dh.setUserId(Long.valueOf(id));
            dh.setDepartmentId(d.getId());
            dh.setType(AdminConstant.HEADER_TYPE_VICE);
            departmentHeaderRepository.save(dh);
        }
        // 手动删除所有部门缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        // 删除所有用户缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable long[] ids){

        for(long id : ids){
            //deleteRecursion(id, ids);
        }
        // 手动删除所有部门缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        // 删除数据权限缓存
        Set<String> keysUserRoleData = redisTemplate.keys("userRole::depIds:" + "*");
        redisTemplate.delete(keysUserRoleData);
        return ResultUtil.success("批量通过id删除数据成功");
    }

    public void deleteRecursion(long id, long[] ids) throws Exception {

        List<User> list = userService.findByDepartmentId(id);
        if(list!=null&&list.size()>0){
            throw new Exception("删除失败，包含正被用户使用关联的部门");
        }
        // 获得其父节点
        Department dep = departMentRepository.getOne(id);
        Department parent = null;
        if(dep!=null){
            parent = departmentService.findByParentId(dep.getParentId());
        }
        departMentRepository.deleteById(id);
        // 删除关联数据权限
        roleDepartmentService.deleteByDepartmentId(id);
        // 删除关联部门负责人
        departmentHeaderService.deleteByDepartmentId(id);
        // 判断父节点是否还有子节点
        if(parent!=null){
            List<Department> childrenDeps = departmentService.findByParentIdOrderBySortOrder(parent.getId(), false);
            if(childrenDeps==null||childrenDeps.size()==0){
                parent.setIsParent(false);
                departmentService.update(parent);
            }
        }
        // 递归删除
        List<Department> departments = departmentService.findByParentIdOrderBySortOrder(id, false);
        for(Department d : departments){
            if(!CommonUtil.judgelongIds(d.getId(), ids)){
                deleteRecursion(d.getId(), ids);
            }
        }
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public Result<List<Department>> searchByTitle(@RequestParam String title,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

        List<Department> list = departmentService.findByTitleLikeOrderBySortOrder("%"+title+"%", openDataFilter);
        list = Collections.unmodifiableList(setInfo(list));
        return new ResultUtil<List<Department>>().setData(list);
    }

    public List<Department> setInfo(List<Department> list){

        // lambda表达式
        list.forEach(item -> {
            if(!(AdminConstant.PARENT_ID ==(item.getParentId()))){
                Department parent = departmentService.findByParentId(item.getParentId());
                item.setParentTitle(parent.getTitle());
            }else{
                item.setParentTitle("一级部门");
            }
            // 设置负责人
            item.setMainHeader(departmentHeaderService.findHeaderByDepartmentId(item.getId(), AdminConstant.HEADER_TYPE_MAIN));
            item.setViceHeader(departmentHeaderService.findHeaderByDepartmentId(item.getId(), AdminConstant.HEADER_TYPE_VICE));
        });
        return list;
    }
}
*/
