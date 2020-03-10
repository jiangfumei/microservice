package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "dict_data")
@ApiModel(value = "字典数据")
public class DictData {

    @ApiModelProperty(value = "数据名称")
    private String title;

    @ApiModelProperty(value = "数据值")
    private String value;

    @ApiModelProperty(value = "排序值")
    @Column(name = "sort_order",precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status = AdminConstant.STATUS_NORMAL;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "所属字典")
    @Column(name = "dict_id")
    private long dictId;
}
