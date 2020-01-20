package com.cloud.sysadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "role")
public class Role {
    private long id;
    private Date createTime;
    private Date updateTime;
    private String name;
    private String description;
    private Integer status;
    private Long createBy;
    private Long updateBy;

}
