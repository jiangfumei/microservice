package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "sys_user")
@Entity
public class SysUser extends BaseEntity {

    private String username;

    private String password;

    private String avatar;

    private String email;

    private Boolean enabled = true;

    private int status = 1;

    @OneToMany(targetEntity = SysRole.class, mappedBy = "sysUser", cascade = CascadeType.REFRESH)
    private List<SysRole> roles = new ArrayList<>();

    @OneToMany(targetEntity = SysUserRole.class, mappedBy = "user", cascade = CascadeType.REFRESH)
    private List<SysUserRole> userRoles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
