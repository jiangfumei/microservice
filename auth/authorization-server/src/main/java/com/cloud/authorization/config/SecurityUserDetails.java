package com.cloud.authorization.config;

import com.cloud.authorization.entity.Permission;
import com.cloud.authorization.entity.Role;
import com.cloud.authorization.entity.User;
import com.cloud.common.base.admin.AdminConstant;
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
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
            for (Role role : roles){
                if (StringUtils.isNotBlank(role.getName())){
                    authorityList.add(new SimpleGrantedAuthority(role.getName()));
                }
            }
        }
        return authorityList;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return AdminConstant.USER_STATUS_LOCK.equals(this.getStatus()) ? false : true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return AdminConstant.STATUS_NORMAL.equals(this.getStatus()) ? true : false;
    }


}
