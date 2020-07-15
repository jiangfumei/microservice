package com.cloud.authorization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 平台认证服务器配置,用来授权
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter
{

    //POST /oauth/authorize  授权码模式认证授权接口
    //GET/POST /oauth/token  获取 token 的接口
    //POST  /oauth/check_token  检查 token 合法性接口
    @Resource
    AuthenticationManager authenticationManager; //用来做验证,支持password模式

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    DataSource dataSource;

    /**
     * jwt 对称加密密钥
     */
    @Value("${spring.security.oauth2.resource.jwt.key-value}")
    private String signingKey;

    @Value("${spring.security.oauth2.client.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.accessTokenValiditySeconds}")
    private int accessTokenValiditySeconds;

    @Value("${spring.security.oauth2.client.refreshTokenValiditySeconds}")
    private int refreshTokenValiditySeconds;

    private static final String DEMO_RESOURCE_ID = "order";


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //允许表单验证, 主要是让/oauth/token支持client_id以及client_secret作登录认证
        oauthServer.allowFormAuthenticationForClients();
        oauthServer.tokenKeyAccess("isAuthenticated()")// 开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("permitAll()");// 开启/oauth/check_token验证端口认证权限访问
    }
/*
    *//**
     * 从数据库读取clientDetails相关配置
     * 有InMemoryClientDetailsService 和 JdbcClientDetailsService 两种方式选择
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * client存储方式，此处使用jdbc存储
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 配置token的数据源、自定义的tokenServices等信息,配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);

    }

    /**
     * token的持久化
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 自定义token
     *
     * @return tokenEnhancerChain
     */



    /**
     * jwt token的生成配置
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        converter.setVerifierKey(signingKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        // 这里如果设置为false则不能更新refresh_token，如果需要刷新token的功能需要设置成true
        defaultTokenServices.setSupportRefreshToken(true);
        // 设置上次RefreshToken是否还可以使用 默认为true
        defaultTokenServices.setReuseRefreshToken(false);
        // token有效期自定义设置，默认12小时，此处设置为6小时
        defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 6);
        // refresh_token默认30天,此处设置为8小时
        defaultTokenServices.setRefreshTokenValiditySeconds(60 * 60 * 8);
        return defaultTokenServices;
    }

}