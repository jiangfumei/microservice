package com.cloud.common.base.entity;

import com.cloud.common.base.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sys_dict")
public class SysDict extends BaseEntity {


    public enum Status {
        hidden, show
    }

    private String keyName;

    private String valueName;

    private String remark;

    private String type;

    @Basic
    @Column(columnDefinition = "INT", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.show;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
