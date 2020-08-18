package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.*;

public class SysRolePermission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "menu_id", columnDefinition = "LONG")
    private SysPermission permission;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", columnDefinition = "LONG")
    private SysRole role;

    public SysPermission getPermission() {
        return permission;
    }

    public void setPermission(SysPermission permission) {
        this.permission = permission;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
}
