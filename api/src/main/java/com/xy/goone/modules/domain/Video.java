package com.xy.goone.modules.domain;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fumei.jiang
 * @date 2019-08-06 14:48
 */
@Data
@Entity
@Table(name = "go_video")
@ApiModel(value = "视频表")
public class Video {

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime = new Date();

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "status")
    private int status = AdminConstant.STATUS_NORMAL;//0删除 1正常

    @Basic
    @Column(name = "cover")
    private String cover;

    @Basic
    @Column(name = "live_time")
    private Date liveTime;

    @Basic
    @Column(name = "duration")
    private BigDecimal duration;

    @Basic
    @Column(name = "video_id") //播放id
    private String videoId;

    @Basic
    @Column(name = "is_live")
    private Integer isLive;//直播中 0直播完的 1直播中

    @Transient
    @Column(name = "is_order")
    private Integer isOrder = AdminConstant.STATUS_NORMAL;//预约状态： 0 未预约,1 已预约

    @Basic
    @Column(name = "sd")
    private String sd; //标清

    @Basic
    @Column(name = "hd")
    private String hd;//高清

    @Basic
    @Column(name = "ultra_clear")
    private String ultraClear;//超清

    @Basic
    @Column(name = "is_reserved")
    private Integer isReserved; //是否可预约

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team_id", columnDefinition = "INT", nullable = false)
    private Team team;

    @Basic
    @Column(name = "is_video")
    private Integer isVideo;//是否直播 0直播 1普通视频


    @Transient
    @OneToMany(targetEntity = VideoOrderTask.class, mappedBy = "video", cascade = CascadeType.REFRESH)
    private List<VideoOrderTask> videoOrderTasks;

    @Basic
    @Column(name = "player_ids")
    private String  playerIds;

    @Basic
    @Column(name = "group_id")
    private String groupId;



}
