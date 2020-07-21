package com.xy.goone.modules.vo;

import com.cloud.common.base.entity.AbstractDomainValue;
import com.xy.goone.modules.domain.Member;

public abstract class MemberVo {
    public static class Simple extends AbstractDomainValue<Member>{

        public Simple(Member domain) {
            super(domain);
        }

        public long getId(){return super.domain.getId();}
        public String getPhone(){return super.domain.getPhone();}
        public String getUsername(){return super.domain.getUsername();}
    }
}
