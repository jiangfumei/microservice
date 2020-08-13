package com.cloud.sysadmin.controller;

import com.cloud.common.base.BasicController;
import com.cloud.common.base.entity.SysUser;
import com.cloud.common.base.vo.Result;
import com.cloud.common.util.PageUtil;
import com.cloud.common.util.ResultUtil;
import com.cloud.common.vo.PageVo;
import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.service.SysUserRoleService;
import com.cloud.sysadmin.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Api("用户接口")
@RequestMapping(value = "/user")
@CacheConfig(cacheNames = "user")
public class UserController extends BasicController {
    @Resource
    private SysUserService userService;

    @Resource
    private SysUserRoleService userRoleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getString() {
        return "hello";
    }


    @ApiOperation("保存-编辑用户")
    @PostMapping(value = "/saveOrUpdate")
    public Result<Object> saveOrUpdate(SysUser user) {
        userService.saveOrUpdate(user);
        return new ResultUtil<>().setSuccessMsg(this.i18n("success"));
    }

    public boolean checkUser(String username) {
        Optional<SysUser> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return false;
        }
        return true;
    }

    @ApiOperation("重置密码")
    @PostMapping("/sysuser/resetPassword")
    public Result<Object> resetPassword(long id, String oldPpassword, String mewPassword) {
        return userService.updatePassword(id, oldPpassword, mewPassword);
    }

    @ApiOperation("根据条件获取全部用户")
    @GetMapping("/list")
    public Result<Object> getAllByCondition(SysUser user, SearchVo searchVo, PageVo pageVo) {
        Page<SysUser> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
//        for (SysUser u : page.getContent()){
//            List<SysRole> roles = userRoleService.findByUser(u);
//            List<SysRoleVo> roleVos = roles.stream().map(SysRoleVo::new).collect(Collectors.toList());
//            u.setRoles(roleVos);
//        }

        return ResultUtil.data(page);
    }

    @ApiOperation("删除单个用户")
    @PostMapping("/delete/{id}")
    public Result<Object> delete(@ApiParam("用户唯一id标识") @PathVariable long id) {
        return userService.delete(id);
    }

    @ApiOperation("批量删除用户")
    @PostMapping("/batchDelete")
    public Result<Object> batchDelete(@ApiParam(name = "ids", value = "用户ID数组", required = true) @RequestParam("ids[]") long[] ids) {
        return userService.batchDelete(ids);
    }


}
