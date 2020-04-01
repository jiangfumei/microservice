package com.cloud.authorization.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@ApiModel(value = "菜单权限表")
public class Permission implements Serializable {

    private long id;

    private Date createTime = new Date();

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "始终显示 默认是")
    private Boolean showAlways = true;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "类型 0顶部菜单 1页面 2具体操作")
    private Integer type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;

    @ApiModelProperty(value = "前端组件")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "说明备注")
    private String description;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "网页链接")
    private String url;

    @ApiModelProperty(value = "子菜单/权限")
    private List<Permission> children;

    @ApiModelProperty(value = "页面拥有的权限类型")
    private List<String> permTypes;

    @ApiModelProperty(value = "节点展开 前端所需")
    private Boolean expand = true;

    @ApiModelProperty(value = "是否勾选 前端所需")
    private Boolean checked = false;

    @ApiModelProperty(value = "是否选中 前端所需")
    private Boolean selected = false;

    private Set<Role> roles;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Integer status = AdminConstant.STATUS_NORMAL;


}
