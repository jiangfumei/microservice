package com.xy.goone.modules.domain;

import com.cloud.common.base.admin.AdminConstant;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fumei.jiang
 * @date 2019-08-06 14:48
 */
@Data
@Entity
@Table(name = "go_team")
public class Team {

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
    @Column(name = "status")
    private int status = AdminConstant.STATUS_NORMAL;

    @Transient
    @OneToMany(targetEntity = Player.class, mappedBy = "team", cascade = CascadeType.REFRESH)
    private List<Player> players = new ArrayList<>();

    @OneToMany(targetEntity = Video.class, mappedBy = "team", cascade = CascadeType.REFRESH)
    @Transient
    private List<Video> videos = new ArrayList<>();
}
