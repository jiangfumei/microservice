package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@ApiModel(value = "角色表")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "create_time", nullable = true)
    private Date createTime = new Date();

    @Column(name = "update_time", nullable = true)
    private Date updateTime;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType = AdminConstant.DATA_TYPE_ALL;


    @ApiModelProperty(value = "角色说明")
    private String description;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status = AdminConstant.STATUS_NORMAL;

    @Basic
    @Column(name = "create_by", nullable = true)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @Basic
    @Column(name = "update_by", nullable = true)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",columnDefinition = "Long",nullable = false)
    private User user;

    /**
     * 角色<->资源 多对多关系，设置级联删除，懒加载，中间表tb_role_resource，[role_id<->resource_id]
     */
    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
            @JoinColumn(name = "permission_id") })
    private java.util.Set<Permission> permissions;

    // 用户 - 角色关系定义;
    // 一个角色对应多个用户
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
   // @JsonIgnore
    private Set<User> users;

    @Transient
    @JsonIgnore
    @ApiModelProperty(value = "拥有权限")
    private List<RolePermission> rolePermissions;

    @Transient
    @JsonIgnore
    @ApiModelProperty(value = "拥有数据权限")
    private List<RoleDepartment> departments;



}
