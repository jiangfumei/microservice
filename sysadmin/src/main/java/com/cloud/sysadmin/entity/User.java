package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "用户表")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "create_time", nullable = true)
    private Date createTime = new Date();

    @Basic
    @Column(name = "update_time", nullable = true)
    private Date updateTime;

    @Basic
    @Column(name = "username", nullable = true, length = 100)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Basic
    @Column(name = "password", nullable = true, length = 150)
    @ApiModelProperty(value = "密码")
    private String password;

    @Basic
    @Column(name = "avatar", nullable = true, length = 250)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @Basic
    @Column(name = "status", nullable = true)
    @ApiModelProperty(value = "状态")
    private Integer status = AdminConstant.STATUS_NORMAL;

    @Basic
    @Column(name = "create_by", nullable = true)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @Basic
    @Column(name = "update_by", nullable = true)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @Transient
    @ApiModelProperty(value = "用户拥有角色")
    @OneToMany(targetEntity = Role.class,mappedBy = "user",cascade = CascadeType.REFRESH)
    private List<Role> roles;

    @Transient
    @ApiModelProperty(value = "用户拥有的权限")
    @OneToMany(targetEntity = Permission.class,mappedBy = "user",cascade = CascadeType.REFRESH)
    private List<Permission> permissions;

}
