package com.cloud.authorization.config;

import com.cloud.authorization.service.AuthenticationService;
import com.cloud.common.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    GlobalExceptionHandler handler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new ApiAuthenticationFilter("/api/**", authenticationService), //
                UsernamePasswordAuthenticationFilter.class);
        http//
                .requestMatchers().antMatchers("/**").and()//
                .authorizeRequests()
                .antMatchers("/api/**/i/**", "/api/**/me/**")
                .hasAnyRole(AuthenticationService.AuthorityType.Register.name())
                .antMatchers("/api/**/v/**")
                .hasAnyRole(AuthenticationService.AuthorityType.Vistor.name())
                .antMatchers("/admin/**")
                .hasAnyRole(AuthenticationService.AuthorityType.Admin.name())
                .anyRequest().permitAll()
                .and().csrf().disable()//
                .sessionManagement().disable()//禁用 session
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin()
                .loginProcessingUrl("/login.do").permitAll();

        http.exceptionHandling().authenticationEntryPoint((r, s, e) -> {
            handler.requestException(r, s, new AuthException.NoLogin("用户没有登录", e));
        }).accessDeniedHandler((r, s, e) -> {
            handler.requestException(r, s, new AuthException.NoRight("登录用户没有权限", e));
        });
    }

    public static class ApiAuthenticationFilter extends GenericFilterBean {
        private RequestMatcher requiresAuthenticationRequestMatcher;
        AuthenticationService redis;

        protected ApiAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationService redis) {
            this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(defaultFilterProcessesUrl);
            this.redis = redis;
        }

        @Override
        public void doFilter(ServletRequest r, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) r;
            SecurityContextHolder.getContext().setAuthentication(null);
            if (requiresAuthenticationRequestMatcher.matches(request)) {
                String token = request.getHeader("token");
                if (!StringUtils.isBlank(token)) {//
                    Optional<AuthenticationService.ApiToken> auth = redis.get(token);
                    if (auth.isPresent()) {
                        if (auth.get().isAuthenticated()) {
                            SecurityContextHolder.getContext().setAuthentication(auth.get());
                        }
                    }
                }
            }
            chain.doFilter(request, response);
        }
    }

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
