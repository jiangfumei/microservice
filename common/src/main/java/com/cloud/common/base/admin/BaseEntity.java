package com.cloud.common.base.admin;

import lombok.Data;

@Data
public class BaseEntity {

    private int status = AdminConstant.STATUS_NORMAL;//默认正常状态
}
