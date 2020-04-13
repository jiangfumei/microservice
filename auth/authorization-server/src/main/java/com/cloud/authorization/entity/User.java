package com.cloud.authorization.entity;

import com.cloud.common.base.admin.AdminConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "用户表")
public class User implements Serializable {
    private long id;

    private Date createTime = new Date();


    private Date updateTime;


    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;


    @ApiModelProperty(value = "密码")
    private String password;


    @ApiModelProperty(value = "头像")
    private String avatar;


    @ApiModelProperty(value = "状态")
    private Integer status = AdminConstant.STATUS_NORMAL;


    @ApiModelProperty(value = "创建者")
    private Long createBy;


    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @JsonIgnore
    private java.util.List<Role> roles;

    @ApiModelProperty(value = "所属部门id")
    private long departmentId;

    @ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;

    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type = AdminConstant.STATUS_NORMAL;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机")
    private String phone;


}
