package com.cloud.sysadmin.controller;

import com.cloud.common.base.admin.AdminConstant;
import com.cloud.common.base.vo.Result;
import com.cloud.common.util.ResultUtil;
import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.entity.UserRole;
import com.cloud.sysadmin.repository.UserRepository;
import com.cloud.sysadmin.repository.UserRoleRepository;
import com.cloud.sysadmin.service.RoleService;
import com.cloud.sysadmin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api("用户接口")
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserRepository userRepository;

    @Resource
    RoleService roleService;

    @Resource
    UserRoleRepository userRoleRepository;


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



}
