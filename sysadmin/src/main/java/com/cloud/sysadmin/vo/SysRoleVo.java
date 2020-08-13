package com.cloud.sysadmin.vo;

import com.cloud.common.base.entity.AbstractDomainValue;
import com.cloud.common.base.entity.SysRole;

public class SysRoleVo extends AbstractDomainValue<SysRole> {
    public SysRoleVo(SysRole domain) {
        super(domain);
    }

    public long getId() {
        return this.domain.getId();
    }

    public String getName() {
        return this.domain.getName();
    }

    public String getDescription() {
        return this.domain.getDescription();
    }
}
