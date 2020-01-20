package com.cloud.sysadmin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user_role", schema = "microservice", catalog = "")
public class UserRole {
    private long id;

}
