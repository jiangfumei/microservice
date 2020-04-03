package com.cloud.authorization.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }

    /**
     * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
   /* @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }*/

    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




   /* *//**
     * 为了方便，使用内存模式，在内存中创建一个用户 user 密码 123456。
     * @param auth
     * @throws Exception
     *//*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //@formatter:off
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER");

        //@formatter:on
    }



    /**
     * 指定密码的加密方式
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
