package com.xy.goone.modules.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@ApiModel(value = "用户选手关联表")
@Table(name = "go_user_player")
public class UserPlayer {
    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "player_id")
    private int palyerId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "status")
    private int status;

}
