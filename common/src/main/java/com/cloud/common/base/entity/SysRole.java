package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity {

    private String name;

    private String code;

    private String description;

    private int sort = 0;

    private int status = 1;

    @OneToMany(targetEntity = SysUserRole.class, mappedBy = "sysRole", cascade = CascadeType.REFRESH)
    private List<SysUserRole> userRoles;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sys_user_id", columnDefinition = "LONG")
    private SysUser sysUser;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public List<SysUserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<SysUserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
