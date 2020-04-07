package com.xy.goone.modules.domain;

import com.cloud.common.base.api.ApiConstant;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fumei.jiang
 * @date 2019-08-06 14:48
 */
@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime = new Date();

    @Basic
    @Column(name = "nick_name")
    private String nickName;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "email")
    private String email;


    @Basic
    @Column(name = "status")
    private int status = ApiConstant.STATUS_NORMAL;

    private String phone;

}
