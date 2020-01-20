package com.cloud.sysadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "user")
public class User {
    private long id;
    private Date createTime;
    private Date updateTime;
    private String username;
    private String password;
    private String avatar;
    private Integer status;
    private Long createBy;
    private Long updateBy;

}
