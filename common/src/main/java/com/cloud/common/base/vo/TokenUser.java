package com.cloud.common.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor//全参构造函数
public class TokenUser implements Serializable {

    public String username;

    public List<String> permissions;

    public Boolean saveLogin;
}
