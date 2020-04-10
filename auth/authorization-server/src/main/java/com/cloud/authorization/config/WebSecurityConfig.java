package com.cloud.authorization.config;


import com.cloud.authorization.service.CustomDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomDetailsService customDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.requestMatchers()指定了哪些请求会被匹配上,requestMatchers()是指定将Spring安全配置应用于哪些请求。
        //.authorizeRequests()该方法用于配置权限
        //对配置的url不进行验证
        http.csrf().disable();
        http
                .requestMatchers().antMatchers( "/index.html", "/api/**","/login**",
                "/actuator/**", "/resource/**","/swagger-resources/**", "/swagger-ui.html", "/configuration/**")//
                .and()
                .authorizeRequests()
                .antMatchers("/registe","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }


    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    /**
     * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customDetailsService).passwordEncoder(passwordEncoder());
        //auth.userDetailsService(clientDetailsService).passwordEncoder(passwordEncoder());
    }


    /**
     * 指定密码的加密方式
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 跨域请求
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



}
