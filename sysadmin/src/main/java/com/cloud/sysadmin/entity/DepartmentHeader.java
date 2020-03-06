package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "department_header")
@ApiModel(value = "部门负责人")
@Data
public class DepartmentHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ApiModelProperty(value = "关联部门id")
    private long departmentId;

    @ApiModelProperty(value = "关联部门负责人")
    private long userId;

    @ApiModelProperty(value = "负责人类型 默认0主要 1副职")
    private Integer type = AdminConstant.HEADER_TYPE_MAIN;

}
