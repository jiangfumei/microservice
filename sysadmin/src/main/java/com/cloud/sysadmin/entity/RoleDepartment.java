package com.cloud.sysadmin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_department")
@ApiModel(value = "角色部门")
public class RoleDepartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ApiModelProperty(value = "角色id")
    @Column(name = "role_id")
    private long roleId;

    @ApiModelProperty(value = "部门id")
    @Column(name = "department_id")
    private long departmentId;
}
