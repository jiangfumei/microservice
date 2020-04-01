package com.cloud.authorization.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "角色权限表")
public class RolePermission implements Serializable {

    private long id;

    private long roleId;

    private long permissionId;

}
