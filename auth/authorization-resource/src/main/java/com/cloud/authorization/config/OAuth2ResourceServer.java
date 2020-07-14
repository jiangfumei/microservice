package com.cloud.authorization.config;

import com.cloud.authorization.converter.CustomAccessTokenConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源服务器
 *多个授权服务可以共用一台资源服务
 * 与授权服务器完全分离且可独立部署，这就是我们为何要在新配置中再次声明一些相同的 bean 的原因
 */

@Configuration
@EnableResourceServer
@Slf4j
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Value("${spring.security.oauth2.resource.jwt.key-value}")
    private String signingKey;

    @Value("${spring.security.oauth2.client.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.clientSecret}")
    private String secret;


    @Autowired
    CustomAccessTokenConverter customAccessTokenConverter;

    private static final String DEMO_RESOURCE_ID = "order";


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().and().authorizeRequests().antMatchers("/user/login", "/user/registe").permitAll()
                .antMatchers("/springjwt/**").authenticated();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        converter.setVerifierKey(signingKey);
        //converter.setAccessTokenConverter(customAccessTokenConverter);
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


    /**
     * 定义OAuth2请求匹配器
     */
    private static class OAuth2RequestedMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            //判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头,或者是请求参数中包含access_token参数,满足其中一个则匹配成功
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token")!=null;
            return haveOauth2Token || haveAccessToken;
        }
    }
}
