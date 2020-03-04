package com.cloud.authorization.config;

import com.cloud.authorization.config.propertie.IgnoredUrlsProperties;
import com.cloud.authorization.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailService userDetailService;

    @Resource
    IgnoredUrlsProperties ignoredUrlsPropertie;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        // 除配置文件忽略路径其它所有请求都需经过认证和授权
        for(String url:ignoredUrlsPropertie.getUrls()){
            registry.antMatchers(url).permitAll();
        }
        registry.and()
                //表单登录方式
                .formLogin()
                //登录页面名称
                .loginPage("sysadmin/common/needLogin")
                //登陆请求url登录表单提交的路径
                .loginProcessingUrl("sysadmin/login")
                .permitAll()
                //成功处理类

                .and()
                .
*/


        http//
                .requestMatchers().antMatchers("/", "/resources/**","/login", "/registe","/api/**", //
                "/v2/**", "/swagger-resources/**", "/swagger-ui.html", "/configuration/**")//
                .and()//
                .authorizeRequests().antMatchers("/resources/**").authenticated().anyRequest().permitAll()//
                .and()
                // 关闭 csrf 防护，因为对于我们的所有请求来说，都是需要携带身份信息的
                .csrf().disable().httpBasic();
    }

    /**
     * @Description 主要解决客户端跨域问题
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
     *
     * @param auth
     * @throws Exception
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
