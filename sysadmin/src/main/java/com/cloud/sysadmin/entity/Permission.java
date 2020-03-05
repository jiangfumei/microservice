package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "Permission")
@ApiModel(value = "菜单权限表")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "create_time", nullable = true)
    private Date createTime = new Date();

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "始终显示 默认是")
    private Boolean showAlways = true;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @Column(name = "type")
    @ApiModelProperty(value = "类型 0顶部菜单 1页面 2具体操作")
    private Integer type;

    @Column(name = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "path")
    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;

    @Column(name = "component")
    @ApiModelProperty(value = "前端组件")
    private String component;

    @Column(name = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    @Column(name = "buttonType")
    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;

    @Column(name = "parentId")
    @ApiModelProperty(value = "父id")
    private Long parentId;

    @Column(name = "description")
    @ApiModelProperty(value = "说明备注")
    private String description;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Column(name = "url")
    @ApiModelProperty(value = "网页链接")
    private String url;

    @Column(name = "status")
    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Integer status = AdminConstant.STATUS_NORMAL;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",columnDefinition = "Long",nullable = false)
    private User user;


}
