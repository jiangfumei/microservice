package com.cloud.common.base;

import com.cloud.common.base.entity.SysUser;
import com.cloud.common.util.PageDATA;
import org.apache.catalina.User;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicController {

    @Resource
    ValueOperations<String, Serializable> redis;

    @Resource
    MessageSource messageSource;

    public static final ThreadLocal<User> local = new ThreadLocal<>();


    protected MessageSource getMessageSource() {
        return messageSource;
    }

    public String i18n(String code, Object... args) throws NoSuchMessageException {
        Locale locale = LocaleContextHolder.getLocale();//如果是根据应用部署的服务器系统来决定国际化
        return messageSource.getMessage(code, args, locale);
    }


    protected SysUser getLoginUser() {
        SysUser info = (SysUser) local.get();
        if (info == null) {
            throw new RuntimeException("Not find Login user !");
        }
        return info;
    }
    public <T, U> PageDATA<U> convert(Page<T> page, Function<T, U> fun) {
        List<U> list = page.getContent().stream().map(fun).collect(Collectors.toList());
        return new PageDATA<>(list, page.getPageable(), page.getTotalElements());
    }
}
