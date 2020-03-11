package com.cloud.authorization.admin.security;

import com.cloud.authorization.admin.security.permission.MyFilterSecurityInterceptor;
import com.cloud.authorization.admin.security.propertie.IgnoredUrlsProperties;
import com.cloud.authorization.admin.security.propertie.SysAdminProperties;
import com.cloud.authorization.admin.jwt.AuthenticationFailHandler;
import com.cloud.authorization.admin.jwt.JWTAuthenticationFilter;
import com.cloud.authorization.admin.jwt.RestAccessDeniedHandler;
import com.cloud.authorization.admin.security.validate.ImageValidateFilter;
import com.cloud.authorization.service.UserDetailService;
import com.cloud.sysadmin.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailService userDetailService;  // 自定义UserDetailsService,将用户权限交给springsecurity进行管控

    @Resource
    IgnoredUrlsProperties ignoredUrlsPropertie;

    @Resource
    private AuthenticationSuccessHandler successHandler;

    @Resource
    private AuthenticationFailHandler failHandler;

    @Resource
    private RestAccessDeniedHandler accessDeniedHandler;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SecurityUtil securityUtil;

    @Resource
    SysAdminProperties properties;

    @Resource
    MyFilterSecurityInterceptor myFilterSecurityInterceptor; //权限管理过滤器

    @Resource
    ImageValidateFilter imageValidateFilter;


    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http//
                .requestMatchers().antMatchers("/", "/resources/**","/login", "/registe","/api/**", //
                "/v2/**", "/swagger-resources/**", "/swagger-ui.html", "/configuration/**")//
                .and()//
                .authorizeRequests().antMatchers("/resources/**").authenticated().anyRequest().permitAll()//
                .and()
                // 关闭 csrf 防护，因为对于我们的所有请求来说，都是需要携带身份信息的
                .csrf().disable().httpBasic();
    }
*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        // 除配置文件忽略路径其它所有请求都需经过认证和授权
        for(String url:ignoredUrlsPropertie.getUrls()){
            registry.antMatchers(url).permitAll();
        }

        registry.and()
                // 表单登录方式
                .formLogin()
                .loginPage("/admin/common/needLogin")
                // 登录请求url
                .loginProcessingUrl("/admin/login")
                .permitAll()
                // 成功处理类
                .successHandler(successHandler) //自定义的登录成功处理器
                // 失败
                .failureHandler(failHandler) //自定义登录失败处理器
                .and()
                // 允许网页iframe
                .headers().frameOptions().disable()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                // 任何请求
                .anyRequest()
                // 需要身份认证
                .authenticated()
                .and()
                // 允许跨域
                .cors().and()
                // 关闭跨站请求防护
                .csrf().disable()
                // 前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 自定义权限拒绝处理类
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                // 添加自定义权限过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 图形验证码过滤器
                .addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加JWT过滤器 除已配置的其它请求都需经过此过滤器
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), properties, redisTemplate, securityUtil));
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
