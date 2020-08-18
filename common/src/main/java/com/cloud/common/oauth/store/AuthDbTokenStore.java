package com.cloud.common.oauth.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

public class AuthDbTokenStore {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenStore tokenStore(){return new JdbcTokenStore(dataSource);
    }
}
