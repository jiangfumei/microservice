package com.cloud.authorization.config;

import com.cloud.authorization.service.UserDetailsServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecuriyConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailsServiceImp userDetailsServiceImp;

/*

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password("{noop}123456") // passwordEncoder.encode("123456")
                .roles("ADMIN");
    }

    */
/**
     * spring security不做拦截处理的请求，忽略静态文件
     *//*

    @Override
    public void configure(WebSecurity web) throws Exception {
        // @formatter:off
        web.ignoring().antMatchers(
                "/favicon.ico", // 浏览器tab页图标
                "/static/**",
                "/images/**",
                "/resources/**",
                "/oauth/uncache_approvals",
                "/oauth/cache_approvals"
        );
        // @formatter:on


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }
*/

    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     * 不定义没有password grant_type
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp)
                .passwordEncoder(passwordEncoder());
    }
}
