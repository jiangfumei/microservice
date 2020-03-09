package com.cloud.sysadmin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "dict")
@ApiModel(value = "字典")
public class Dict implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "create_time", nullable = true)
    private Date createTime = new Date();

    @Column(name = "update_time", nullable = true)
    private Date updateTime;

    @ApiModelProperty(value = "字典名称")
    private String title;

    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Basic
    @Column(name = "create_by", nullable = true)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @Basic
    @Column(name = "update_by", nullable = true)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;
}
