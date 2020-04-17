package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "用户表")
public class User implements Serializable {
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

    @ApiModelProperty(value = "邮箱")
    private String email;

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

    /**
     * 用户<->角色 多对多关系，设置级联删除，懒加载，中间表user_role，[user_id<->role_id]
     */
    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "department_id", columnDefinition = "LONG", nullable = false)
    private Department department;


    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type = AdminConstant.USER_COMMON;

    @ApiModelProperty(value = "性别:0男 1女")
    private Integer sex;

    @ApiModelProperty(value = "手机")
    private String phone;

}
