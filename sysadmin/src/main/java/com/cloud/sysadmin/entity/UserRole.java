package com.cloud.sysadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_role", schema = "microservice", catalog = "")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "role_id")
    private long roleId;
}
