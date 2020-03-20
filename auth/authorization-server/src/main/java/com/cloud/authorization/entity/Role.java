package com.cloud.authorization.entity;

import com.cloud.common.base.admin.AdminConstant;
import com.cloud.sysadmin.entity.Department;
import com.cloud.sysadmin.entity.RoleDepartment;
import com.cloud.sysadmin.entity.RolePermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@ApiModel(value = "角色表")
public class Role implements Serializable {

    private long id;


    private Date createTime = new Date();

    private Date updateTime;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType = AdminConstant.DATA_TYPE_ALL;


    @ApiModelProperty(value = "角色说明")
    private String description;

    private Integer status = AdminConstant.STATUS_NORMAL;


    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;


    private List<Permission> permissions;


    private Set<User> users;


    private List<RolePermission> rolePermissions;

    private List<RoleDepartment> departments;


    private List<Department> departmentSet;



}
