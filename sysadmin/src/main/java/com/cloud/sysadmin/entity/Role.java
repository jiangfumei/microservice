package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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

    @Basic
    @ApiModelProperty("状态")
    @Column(name = "status", nullable = true)
    private Integer status = AdminConstant.STATUS_NORMAL;

    /**
     * 角色<->权限 多对多关系，设置级联删除，懒加载，中间表role_resource，[role_id<->permission_id]
     */
    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
            @JoinColumn(name = "permission_id") })
    private Set<Permission> permissions;

    // 用户 - 角色关系定义;
    // 一个角色对应多个用户
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;



}
