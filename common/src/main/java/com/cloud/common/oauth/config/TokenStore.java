package com.cloud.common.oauth.config;

import com.cloud.common.oauth.store.AuthDbTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public class TokenStore {

    //通过@ConditionalOnProperty控制配置类是否生效,可以将配置与代码进行分离,实现了更好的控制配置.
    //@ConditionalOnProperty实现是通过havingValue与配置文件中的值对比,返回为true则配置类生效,反之失效.
    //通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值。
    //如果该值为空，则返回false;
    //如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
    @Configuration
    @ConditionalOnProperty(prefix="microservice.oauth2.token.store",name = "type",havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig{

    }


}
