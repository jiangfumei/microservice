package com.cloud.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务器
 *多个授权服务可以共用一台资源服务
 * 与授权服务器完全分离且可独立部署，这就是我们为何要在新配置中再次声明一些相同的 bean 的原因
 */

@Configuration
@EnableResourceServer
@Slf4j
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "messages-resource";

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    public void configure(ResourceServerSecurityConfigurer securityConfigurer) throws Exception{
        securityConfigurer.resourceId(RESOURCE_ID)
                .tokenStore(this.tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .antMatcher("/messages/**")
                .authorizeRequests()
                .antMatchers("/messages/**").access("#oauth2.hasScope('message.read')");
        // @formatter:on
    }

}
