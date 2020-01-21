package com.cloud.sysadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "Permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "showAlways")
    private Byte showAlways;

    @Column(name = "level")
    private Integer level;

    @Column(name = "type")
    private Integer type;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @Column(name = "component")
    private String component;

    @Column(name = "icon")
    private String icon;

    @Column(name = "buttonType")
    private String buttonType;

    @Column(name = "parentId")
    private Long parentId;

    @Column(name = "description")
    private String description;

    @Column(name = "sortOrder")
    private Byte sortOrder;

    @Column(name = "url")
    private String url;


}
