package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_permission")
public class SysPermission extends BaseEntity implements Serializable {

    public enum Type {
        directory, menu, button
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pid", columnDefinition = "LONG")
    private SysPermission permission;

    private String name;

    private String value;

    private String icon;

    @Basic
    @Column(columnDefinition = "INT", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type = Type.menu;

    private String uri;

    private int sprt = 0;

    public SysPermission getPermission() {
        return permission;
    }

    public void setPermission(SysPermission permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getSprt() {
        return sprt;
    }

    public void setSprt(int sprt) {
        this.sprt = sprt;
    }

}
