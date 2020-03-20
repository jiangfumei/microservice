package com.xy.goone.modules.domain;

import com.cloud.common.base.admin.AdminConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author fumei.jiang
 * @date 2019-08-06 14:48
 */
@Data
@Entity
@Table(name = "go_user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class User {

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime = new Date();


    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "jp_last_name")
    private String jpLastName;

    @Basic
    @Column(name = "jp_first_name")
    private String jpFirstName;

    @Basic
    @Column(name = "nick_name")
    private String nickName;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "email")
    private String email;

    @Deprecated
    @Basic
    @Column(name = "gift_quantity")
    private Integer giftQuantity = 0;

    @Basic
    @Column(name = "expire_time", columnDefinition = "TIMESTAMP", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date expireTime;

    @Basic
    @Column(name = "status")
    private int status = AdminConstant.STATUS_NORMAL;

    @Basic
    @Column(name = "type")
    private int type;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "user_sig")
    private String user_sig;

    //用户：普通用户0、会员1、超级会员2
    //普通用户免费试用一个月，会员、超级会员分别是每月消费980、10000，可以退会
    //刚开始注册时默认为普通用户，购买会员时调取内购或google支付接口，逻辑改变用户表的会员状态，退订时在有效期之外也相应改变会员状态
    @Basic
    @Column(name = "no")
    private String no;

    @Basic
    @Column(name = "join_time", columnDefinition = "TIMESTAMP", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date joinTime;//入会时间

    @Basic
    @Column(name = "uuid")
    private String uuid;

    @Basic
    @Column(name = "coin")
    private Integer coin = 0;//RIZIN虚拟币


    @JsonIgnore
    @OneToMany(targetEntity = VideoOrderTask.class, mappedBy = "user", cascade = CascadeType.REFRESH)
    private List<VideoOrderTask> videoOrderTasks;


}
