package com.xy.goone.modules.domain;

import com.cloud.common.base.entity.AbstractUser;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fumei.jiang
 * @date 2019-08-06 14:48
 */
@Entity
@Table(name = "member")
public class Member extends AbstractUser {

    private String address;

    private String phone;

    private String nickName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
