package com.cloud.authorization.admin.security;

import com.cloud.common.base.admin.AdminConstant;
import com.cloud.sysadmin.entity.Permission;
import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class SecurityUserDetails extends User implements UserDetails {

    public SecurityUserDetails(User user) {

        if(user!=null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.setRoles(user.getRoles());
            this.setPermissions(user.getPermissions());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<Permission> permissions = this.getPermissions();
        // 添加请求权限
        if(permissions!=null&&permissions.size()>0){
            for (Permission permission : permissions) {
                if(AdminConstant.PERMISSION_OPERATION.equals(permission.getType())
                        && StringUtils.isNotBlank(permission.getTitle())
                        &&StringUtils.isNotBlank(permission.getPath())) {

                    authorityList.add(new SimpleGrantedAuthority(permission.getTitle()));//1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                }
            }
        }
        // 添加角色
        List<Role> roles = this.getRoles();
        if(roles!=null&&roles.size()>0){
            // lambda表达式
            roles.forEach(item -> {
                if(StringUtils.isNotBlank(item.getName())){
                    authorityList.add(new SimpleGrantedAuthority(item.getName()));
                }
            });
        }
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {//账户是否过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//账户是否禁用
        return AdminConstant.USER_STATUS_LOCK.equals(this.getStatus()) ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//密码是否过期
        return false;
    }

    @Override
    public boolean isEnabled() {//是否启用
        return AdminConstant.STATUS_NORMAL.equals(this.getStatus()) ? true : false;
    }
}
