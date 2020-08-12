package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sys_dict")
public class SysDict extends BaseEntity {

    public enum Status{
        hidden,show
    }

    private String keyName;

    private String valueName;

    private String remark;

    private String type;

    @Basic
    @Column(columnDefinition = "INT", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.show;



}
