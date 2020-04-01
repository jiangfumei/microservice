package com.cloud.authorization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "角色部门")
public class RoleDepartment implements Serializable {


    private long id;

    @ApiModelProperty(value = "角色id")
    private long roleId;

    @ApiModelProperty(value = "部门id")
    private long departmentId;
}
