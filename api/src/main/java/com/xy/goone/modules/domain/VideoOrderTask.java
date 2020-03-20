package com.xy.goone.modules.domain;

import com.cloud.common.base.admin.AdminConstant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhuhai.mao
 * @date 2019-08-09
 */
@Data
@Entity
@Table(name = "go_video_order_task")
@ApiModel(value = "视频表")
public class VideoOrderTask {

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime = new Date();

    @Basic
    @Column(name = "order_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date orderTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "video_id", columnDefinition = "INT", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", columnDefinition = "INT", nullable = false)
    private User user;

    @Basic
    @Column(name ="status")
    private int status = AdminConstant.STATUS_NORMAL;//默认正常状态

    @Basic
    @Column(name = "cover")
    private String cover;

    @Basic
    @Column(name = "title")
    private String title;
}
