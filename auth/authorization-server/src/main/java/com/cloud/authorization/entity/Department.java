package com.cloud.authorization.entity;

import com.cloud.common.base.base.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="部门表")
public class Department implements Serializable {

    private long id;

    private Date createTime = new Date();

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty(value = "父id")
    private long parentId;

    @ApiModelProperty(value = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Integer status = AdminConstant.STATUS_NORMAL;

    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    @ApiModelProperty(value = "主负责人")
    private List<Long> mainHeader;

    @ApiModelProperty(value = "副负责人")
    private List<Long> viceHeader;

    private java.util.Set<Role> roles;



}
