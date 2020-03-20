package com.xy.goone.modules.domain;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fumei.jiang
 * @date 2019-08-06 14:48
 */
@Data
@Entity
@ApiModel(value = "选手表")
@Table(name = "go_player")
public class Player {

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime = new Date();

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "fans_quantity")
    private Integer fansQuantity;

    @Basic
    @Column(name = "coin")
    private Integer coin;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "status")
    private int status= AdminConstant.STATUS_NORMAL;//0删除 1正常

    @Basic
    @Column(name = "gift_quantity")
    private Integer giftQuantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team_id", columnDefinition = "INT", nullable = false)
    private Team team;


}
