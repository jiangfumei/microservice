package com.cloud.sysadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "create_time", nullable = true)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = true)
    private Date updateTime;
    @Basic
    @Column(name = "username", nullable = true, length = 100)
    private String username;

    @Basic
    @Column(name = "password", nullable = true, length = 150)
    private String password;

    @Basic
    @Column(name = "avatar", nullable = true, length = 250)
    private String avatar;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    @Basic
    @Column(name = "create_by", nullable = true)
    private Long createBy;

    @Basic
    @Column(name = "update_by", nullable = true)
    private Long updateBy;

}
