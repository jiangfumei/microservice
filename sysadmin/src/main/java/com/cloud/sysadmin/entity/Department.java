package com.cloud.sysadmin.entity;

import com.cloud.common.base.admin.AdminConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@ApiModel(value="部门表")
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "create_time", nullable = true)
    private Date createTime = new Date();

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty(value = "父id")
    private long parentId;

    @ApiModelProperty(value = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Integer status = AdminConstant.STATUS_NORMAL;

    @Transient
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    @Transient
    @ApiModelProperty(value = "主负责人")
    private List<Long> mainHeader;

    @Transient
    @ApiModelProperty(value = "副负责人")
    private List<Long> viceHeader;

    /**
     * 用户<->角色 多对多关系，设置级联删除，懒加载，中间表user_role，[user_id<->role_id]
     */
    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "role_department", joinColumns = { @JoinColumn(name = "department_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private java.util.Set<Role> roles;



}
