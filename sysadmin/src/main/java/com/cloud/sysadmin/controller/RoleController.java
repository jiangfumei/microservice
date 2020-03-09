package com.cloud.sysadmin.controller;

import com.cloud.common.base.vo.Result;
import com.cloud.common.util.PageUtil;
import com.cloud.common.util.ResultUtil;
import com.cloud.common.vo.PageVo;
import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.RoleDepartment;
import com.cloud.sysadmin.entity.RolePermission;
import com.cloud.sysadmin.entity.UserRole;
import com.cloud.sysadmin.repository.RoleDepartmentRepository;
import com.cloud.sysadmin.repository.RolePermissionRepository;
import com.cloud.sysadmin.repository.RoleRepository;
import com.cloud.sysadmin.service.RoleDepartmentService;
import com.cloud.sysadmin.service.RolePermissionService;
import com.cloud.sysadmin.service.RoleService;
import com.cloud.sysadmin.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/role")
@Slf4j
@Api("角色接口")
@CacheConfig(cacheNames = "role")
public class RoleController {

    @Resource
    RoleService roleService;

    @Resource
    RoleRepository roleRepository;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    UserRoleService userRoleService;

    @Resource
    RolePermissionService rolePermissionService;

    @Resource
    RoleDepartmentService roleDepartmentService;

    @Resource
    RolePermissionRepository rolePermissionRepository;

    @Resource
    RoleDepartmentRepository roleDepartmentRepository;


    @RequestMapping(value = "/getAllList",method = RequestMethod.GET)
    @ApiOperation(value = "获取全部角色")
    public Result<Object> roleGetAll(){

        List<Role> list = roleRepository.findAll();
        return ResultUtil.data(list);
    }

    @RequestMapping(value = "/getAllByPage",method = RequestMethod.GET)
    @ApiOperation(value = "分页获取角色")
    public Result<Page<Role>> getRoleByPage(PageVo page){

        Page<Role> list = roleRepository.findAll(PageUtil.initPage(page));
        for(Role role : list.getContent()){
            // 角色拥有权限
            List<RolePermission> permissions = rolePermissionService.findByRoleId(role.getId());
            role.setRolePermissions(permissions);
            // 角色拥有数据权限
            List<RoleDepartment> departments = roleDepartmentService.findByRoleId(role.getId());
            role.setDepartments(departments);
        }
        return new ResultUtil<Page<Role>>().setData(list);
    }

    @RequestMapping(value = "/setDefault",method = RequestMethod.POST)
    @ApiOperation(value = "设置或取消默认角色")
    public Result<Object> setDefault(@RequestParam long id,
                                     @RequestParam Boolean isDefault){

        Role role = roleRepository.getOne(id);
        if(role==null){
            return ResultUtil.error("角色不存在");
        }
        role.setDefaultRole(isDefault);
        roleService.update(role);
        return ResultUtil.success("设置成功");
    }

    @RequestMapping(value = "/editRolePerm",method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public Result<Object> editRolePerm(@RequestParam long roleId,
                                       @RequestParam(required = false) long[] permIds){

        //删除其关联权限
        rolePermissionService.deleteByRoleId(roleId);
        //分配新权限
        for(long permId : permIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permId);
            rolePermissionRepository.save(rolePermission);
        }
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserPerm = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keysUserPerm);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/editRoleDep",method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配数据权限")
    public Result<Object> editRoleDep(@RequestParam long roleId,
                                      @RequestParam Integer dataType,
                                      @RequestParam(required = false) long[] depIds){

        Role r = roleRepository.getOne(roleId);
        r.setDataType(dataType);
        roleService.update(r);
        // 删除其关联数据权限
        roleDepartmentService.deleteByRoleId(roleId);
        // 分配新数据权限
        for(long depId : depIds){
            RoleDepartment roleDepartment = new RoleDepartment();
            roleDepartment.setRoleId(roleId);
            roleDepartment.setDepartmentId(depId);
            roleDepartmentRepository.save(roleDepartment);
        }
        // 手动删除相关缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);

        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ApiOperation(value = "保存数据")
    public Result<Role> save(Role role){

        Role r = roleRepository.save(role);
        return new ResultUtil<Role>().setData(r);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "更新数据")
    public Result<Role> edit(Role entity){

        Role r = roleService.update(entity);
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return new ResultUtil<Role>().setData(r);
    }

    @RequestMapping(value = "/delAllByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delByIds(@PathVariable long[] ids){

        for(Long id:ids){
            List<UserRole> list = userRoleService.findByRoleId(id);
            if(list!=null&&list.size()>0){
                return ResultUtil.error("删除失败，包含正被用户使用关联的角色");
            }
        }
        for(Long id:ids){
            roleRepository.deleteById(id);
            //删除关联菜单权限
            rolePermissionService.deleteByRoleId(id);
            //删除关联数据权限
            roleDepartmentService.deleteByRoleId(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }


}
