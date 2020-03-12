package com.cloud.authorization.config;

import com.cloud.authorization.service.UserDetailsServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        // 设置手机验证码登陆的AuthenticationProvider
        authenticationManagerBuilder.authenticationProvider(mobileAuthenticationProvider());
    }*/
    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     * 不定义没有password grant_type
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 创建手机验证码登陆的AuthenticationProvider
     *
     * @return mobileAuthenticationProvider
     */
   /* @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider(this.mobileUserDetailsService);
        mobileAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return mobileAuthenticationProvider;
    }*/
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
