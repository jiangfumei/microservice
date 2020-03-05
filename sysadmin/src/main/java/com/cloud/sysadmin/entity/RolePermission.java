package com.cloud.sysadmin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_permission")
@ApiModel(value = "角色权限表")
public class RolePermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "role_id")
    private long roleId;

    @Column(name = "permission_id")
    private long permissionId;

}
