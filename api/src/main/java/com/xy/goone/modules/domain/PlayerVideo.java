package com.xy.goone.modules.domain;

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
@ApiModel(value = "选手视频关联表")
@Table(name = "go_player_video")
public class PlayerVideo {
    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime = new Date();

    @Basic
    @Column(name = "player_id")
    private int playerId;

    @Basic
    @Column(name = "video_id")
    private int videoId;



}
