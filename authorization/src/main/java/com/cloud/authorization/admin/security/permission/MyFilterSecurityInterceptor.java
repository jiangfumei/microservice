package com.cloud.authorization.admin.security.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 权限管理过滤器
 * 监控用户行为
 * MyFilterSecurityInterceptor類，負責過濾url請求
 * MyAccessDecisionManager 权限管理决断器
 * MySecurityMetadataSource 類权限配置资源管理器
 */

@Component
@Slf4j
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    //MySecurityMetadataSource類，負責讀取數據庫中的url對應的權限
    @Resource
    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;//两种基于spring security实现动态权限的方法，一是自定义accessDecisionManager，二是自定义FilterInvocationSecurityMetadataSource。

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {

        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

 /*   public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }
*/

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.customFilterInvocationSecurityMetadataSource;
    }
}
