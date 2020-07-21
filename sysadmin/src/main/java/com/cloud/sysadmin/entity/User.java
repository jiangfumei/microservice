package com.cloud.sysadmin.entity;

import com.cloud.common.base.entity.AbstractUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "用户表")
public class User extends AbstractUser {
   private String sex;
}
