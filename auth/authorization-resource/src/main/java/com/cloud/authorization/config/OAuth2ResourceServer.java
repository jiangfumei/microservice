package com.cloud.authorization.config;

import com.cloud.common.oauth.config.TokenStoreConfig;
import com.cloud.common.oauth.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 资源服务器
 *多个授权服务可以共用一台资源服务
 * 与授权服务器完全分离且可独立部署，这就是我们为何要在新配置中再次声明一些相同的 bean 的原因
 */

@Configuration
@EnableResourceServer
@Import(TokenStoreConfig.class)
@Slf4j
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable()
                .and()
                .requestMatcher(request -> false)
                .authorizeRequests()
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest()
                .authenticated();
    }

}
