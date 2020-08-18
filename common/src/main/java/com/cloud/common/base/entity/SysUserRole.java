package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sys_user_id", columnDefinition = "LONG", nullable = false)
    private SysUser sysUser;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sys_role_id", columnDefinition = "LONG", nullable = false)
    private SysRole sysRole;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

}
