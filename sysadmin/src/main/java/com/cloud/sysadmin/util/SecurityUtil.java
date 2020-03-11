package com.cloud.sysadmin.util;

import com.cloud.common.base.admin.AdminConstant;
import com.cloud.sysadmin.entity.Department;
import com.cloud.sysadmin.entity.Permission;
import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.repository.DepartMentRepository;
import com.cloud.sysadmin.service.DepartmentService;
import com.cloud.sysadmin.service.UserRoleService;
import com.cloud.sysadmin.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Component
@Slf4j
public class SecurityUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private DepartMentRepository departMentRepository;

    /**
     * 获取当前登录用户
     * @return
     */
    public User getCurrUser(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByUsername(user.getUsername());
    }
    /**
     * 获取当前用户数据权限 null代表具有所有权限 包含值为-1的数据代表无任何权限
     */
    public List<String> getDeparmentIds(){

        List<String> deparmentIds = new ArrayList<>();
        User u = getCurrUser();
        // 读取缓存
        String key = "userRole::depIds:" + u.getId();
        String v = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotBlank(v)){
            deparmentIds = new Gson().fromJson(v, new TypeToken<List<String>>(){}.getType());
            return deparmentIds;
        }
        // 当前用户拥有角色
        List<Role> roles = userRoleService.findRolesByUserId(u.getId());
        // 判断有无全部数据的角色
        Boolean flagAll = false;
        for(Role r : roles){
            if(r.getDataType()==null||r.getDataType().equals(AdminConstant.DATA_TYPE_ALL)){
                flagAll = true;
                break;
            }
        }
        // 包含全部权限返回null
        if(flagAll){
            return null;
        }
        // 每个角色判断 求并集
        for(Role r : roles) {
            String departmentId = String.valueOf(u.getDepartmentId());
            if (r.getDataType().equals(AdminConstant.DATA_TYPE_UNDER)) {

                // 本部门及以下
                if (StringUtils.isBlank(departmentId)) {
                    // 用户无部门
                    deparmentIds.add("-1");
                } else {
                    // 递归获取自己与子级
                    List<String> ids = new ArrayList<>();
                    getRecursion(departmentId, ids);
                    deparmentIds.addAll(ids);
                }
            } else if (r.getDataType().equals(AdminConstant.DATA_TYPE_SAME)) {
                // 本部门
                if (StringUtils.isBlank(departmentId)) {
                    // 用户无部门
                    deparmentIds.add("-1");
                } else {
                    deparmentIds.add(departmentId);
                }
            } else if (r.getDataType().equals(AdminConstant.DATA_TYPE_CUSTOM)) {
                // 自定义
                List<String> depIds = userRoleService.findDepIdsByUserId(u.getId());
                if (depIds == null || depIds.size() == 0) {
                    deparmentIds.add("-1");
                } else {
                    deparmentIds.addAll(depIds);
                }
            }
        }
        // 去重
        LinkedHashSet<String> set = new LinkedHashSet<>(deparmentIds.size());
        set.addAll(deparmentIds);
        deparmentIds.clear();
        deparmentIds.addAll(set);
        // 缓存
        redisTemplate.opsForValue().set(key, new Gson().toJson(deparmentIds));
        return deparmentIds;
    }

    private void getRecursion(String departmentId, List<String> ids){

        Department department = departMentRepository.getOne(Long.valueOf(departmentId));
        ids.add(String.valueOf(department.getId()));
        if(department.getIsParent()!=null&&department.getIsParent()){
            // 获取其下级
            List<Department> departments = departmentService.findByParentIdAndStatusOrderBySortOrder(Long.valueOf(departmentId), AdminConstant.STATUS_NORMAL);
            departments.forEach(d->{
                getRecursion(String.valueOf(d.getId()), ids);
            });
        }
    }

    /**
     * 通过用户名获取用户拥有权限
     * @param username
     */
    public List<GrantedAuthority> getCurrUserPerms(String username){

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Permission p : userService.findUserByUsername(username).getPermissions()){
            authorities.add(new SimpleGrantedAuthority(p.getTitle()));
        }
        return authorities;
    }
}
