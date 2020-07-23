package com.cloud.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.sql.DataSource;

/**
 * 平台认证服务器配置,用来授权
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter
{
  private AuthenticationManager authenticationManager;

  private WebResponseExceptionTranslator webResponseExceptionTranslator;

  private DataSource dataSource;

  private RedisConnectionFactory redisConnectionFactory;

  @Autowired
  public OAuth2AuthorizationServer(AuthenticationManager authenticationManager, WebResponseExceptionTranslator webResponseExceptionTranslator, DataSource dataSource, RedisConnectionFactory redisConnectionFactory) {
    this.authenticationManager = authenticationManager;
    this.webResponseExceptionTranslator = webResponseExceptionTranslator;
    this.dataSource = dataSource;
    this.redisConnectionFactory = redisConnectionFactory;
  }

  @Bean
  public TokenService tokenStore(RedisConnectionFactory redisConnectionFactory){
    return null;
  }


}