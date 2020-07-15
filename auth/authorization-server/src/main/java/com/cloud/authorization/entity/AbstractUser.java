package com.cloud.authorization.entity;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@ApiModel(value = "用户表")
@MappedSuperclass
public abstract class AbstractUser  {

    private long id;

    private Date createTime = new Date();


    private Date updateTime;


    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;


    @ApiModelProperty(value = "密码")
    private String password;


    @ApiModelProperty(value = "头像")
    private String avatar;


    @ApiModelProperty(value = "状态")
    private Integer status = AdminConstant.STATUS_NORMAL;


    @ApiModelProperty(value = "uuid")
    @Basic
    @Column(length = 64, nullable = false)
    private String uuid = UUID.randomUUID().toString().replaceAll("-", "");

    public boolean isDisable() {
        return status == -1;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
