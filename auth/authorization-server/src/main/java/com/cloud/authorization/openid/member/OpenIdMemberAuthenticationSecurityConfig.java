package com.cloud.authorization.openid.member;

import com.cloud.authorization.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * openId的相关处理配置
 *
 * @author mall
 */
@Component
public class OpenIdMemberAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private UserSecurityService userDetailsService;

    @Override
    public void configure(HttpSecurity http) {
        //openId provider
        OpenIdMemberAuthenticationProvider provider = new OpenIdMemberAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(provider);
    }
}
