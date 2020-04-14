package com.cloud.sysadmin.controller;

import com.cloud.common.base.admin.AdminConstant;
import com.cloud.common.base.vo.Result;
import com.cloud.common.util.PageUtil;
import com.cloud.common.util.ResultUtil;
import com.cloud.common.vo.PageVo;
import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.entity.Department;
import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.entity.UserRole;
import com.cloud.sysadmin.repository.DepartMentRepository;
import com.cloud.sysadmin.repository.UserRepository;
import com.cloud.sysadmin.repository.UserRoleRepository;
import com.cloud.sysadmin.service.DepartmentHeaderService;
import com.cloud.sysadmin.service.RoleService;
import com.cloud.sysadmin.service.UserRoleService;
import com.cloud.sysadmin.service.UserService;
import com.cloud.sysadmin.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@Api("用户接口")
@RequestMapping(value = "/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserRepository userRepository;

    @Resource
    RoleService roleService;

    @Resource
    SecurityUtil securityUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    DepartMentRepository departMentRepository;

    @Resource
    UserRoleService userRoleService;

    @Resource
    UserRoleRepository userRoleRepository;

    @Resource
    DepartmentHeaderService departmentHeaderService;


    @RequestMapping(value = "/registe",method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public Result<Object> register(User u){
        if (StringUtils.isBlank(u.getUsername())||StringUtils.isBlank(u.getPassword()))
            return ResultUtil.error("缺少表单必填字段");
        if (userService.findUserByUsername(u.getUsername())!=null)
            return ResultUtil.error("该用户名已存在");
        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        u.setStatus(AdminConstant.STATUS_NORMAL);
        User user = userRepository.save(u);
        if (u==null)
            return ResultUtil.error("注册失败");
        // 默认角色
        List<Role> roleList = roleService.findByDefaultRole(true);
        if(roleList!=null&&roleList.size()>0){
            roleList.forEach(e->{
                UserRole ur =new UserRole();
                ur.setRoleId(e.getId());
                ur.setUserId(user.getId());

            });
        }
        return ResultUtil.data(u);
    }

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }


    /**
     * 获取授权的用户信息
     * @param principal 当前用户
     * @return 授权信息
     */
    @GetMapping("current/get")
    public Principal user(Principal principal){
        return principal;
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<User> getUserInfo(){

        User u = securityUtil.getCurrUser();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        u.setPassword(null);
        return new ResultUtil<User>().setData(u);
    }

    @RequestMapping(value = "/unlock",method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password){

        User u = securityUtil.getCurrUser();
        if(!new BCryptPasswordEncoder().matches(password, u.getPassword())){
            return ResultUtil.error("密码不正确");
        }
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    public Result<Object> resetPass(@RequestParam long[] ids){

        for(long id:ids){
            User u = userRepository.getOne(id);
            u.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userService.update(u);
            redisTemplate.delete("user::"+u.getUsername());
        }
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料",notes = "用户名密码不会修改 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public Result<Object> editOwn(User u){

        User old = securityUtil.getCurrUser();
        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if(user==null){
            return ResultUtil.error("修改失败");
        }
        return ResultUtil.success("修改成功");
    }

    /**
     * 线上demo不允许测试账号改密码
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass",method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public Result<Object> modifyPass(@ApiParam("旧密码") @RequestParam String password,
                                     @ApiParam("新密码") @RequestParam String newPass){

        User user = securityUtil.getCurrUser();

        if(!new BCryptPasswordEncoder().matches(password, user.getPassword())){
            return ResultUtil.error("旧密码不正确");
        }

        String newEncryptPass= new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        userService.update(user);

        //手动更新缓存
        redisTemplate.delete("user::"+user.getUsername());

        return ResultUtil.success("修改密码成功");
    }


    @RequestMapping(value = "/getByCondition",method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<Page<User>> getByCondition(User user,
                                             SearchVo searchVo,
                                             PageVo pageVo){

        Page<User> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
        for(User u: page.getContent()){
            // 关联部门
            //if(u.getDepartmentId()!=null){
            Department department = departMentRepository.findById(u.getDepartmentId()).get();
            if(department!=null){
                u.setDepartmentTitle(department.getTitle());
            }
            //}
            // 关联角色
            List<Role> list = userRoleService.findRolesByUserId(u.getId());
            u.setRoles(list);
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }
        return new ResultUtil<Page<User>>().setData(page);
    }


    @RequestMapping(value = "/getByDepartmentId/{departmentId}",method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<List<User>> getByCondition(@PathVariable long departmentId){

        List<User> list = userService.findByDepartmentId(departmentId);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<User>>().setData(list);
    }

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "获取全部用户数据")
    public Result<List<User>> getByCondition(){

        List<User> list = userRepository.findAll();
        for(User u: list){
            // 关联部门
            //if(StringUtils.isNotBlank(u.getDepartmentId())){
            Department department = departMentRepository.findById(u.getDepartmentId()).get();
            if(department!=null){
                u.setDepartmentTitle(department.getTitle());
            }
           // }
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }
        return new ResultUtil<List<User>>().setData(list);
    }

    @RequestMapping(value = "/admin/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public Result<Object> regist(User u,
                                 @RequestParam(required = false) long[] roles){

        if(StringUtils.isBlank(u.getUsername()) || StringUtils.isBlank(u.getPassword())){
            return ResultUtil.error("缺少必需表单字段");
        }

        if(userService.findByUsername(u.getUsername())!=null){
            return ResultUtil.error("该用户名已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User user=userRepository.save(u);
        if(user==null){
            return ResultUtil.error("添加失败");
        }
        if(roles!=null&&roles.length>0){
            //添加角色
            for(Long roleId : roles){
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                userRoleRepository.save(ur);
            }
        }

        return ResultUtil.data(user);
    }

    @RequestMapping(value = "/admin/disable/{userId}",method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public Result<Object> disable(@ApiParam("用户唯一id标识") @PathVariable long userId){

        User user = userRepository.getOne(userId);
        if(user==null){
            return ResultUtil.error("通过userId获取用户失败");
        }
        user.setStatus(AdminConstant.USER_STATUS_LOCK);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::"+user.getUsername());
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/admin/enable/{userId}",method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public Result<Object> enable(@ApiParam("用户唯一id标识") @PathVariable long userId){

        User user = userRepository.getOne(userId);
        if(user==null){
            return ResultUtil.error("通过userId获取用户失败");
        }
        user.setStatus(AdminConstant.STATUS_NORMAL);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::"+user.getUsername());
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delAllByIds(@PathVariable long[] ids){

        for(long id:ids){
            User u = userRepository.getOne(id);
            //删除缓存
            redisTemplate.delete("user::" + u.getUsername());
            redisTemplate.delete("userRole::" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            Set<String> keys = redisTemplate.keys("department::*");
            redisTemplate.delete(keys);
            userRepository.deleteById(id);
            //删除关联角色
            userRoleService.deleteByUserId(id);
            //删除关联部门负责人
            departmentHeaderService.deleteByUserId(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/getByUsername/{name}",method = RequestMethod.POST)
    public User getByUsername(@PathVariable(value = "name") String name){
        return userRepository.findUserByUsername(name);
    }



}
