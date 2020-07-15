package com.cloud.common.base;

import com.cloud.common.exception.HttpRequestException;
import com.cloud.common.util.PageDATA;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicController {

   /* @Autowired
    protected AbstractUserService usersService;*/

    @Resource
    ValueOperations<String, Serializable> redis;

    public static final ThreadLocal<User> local = new ThreadLocal<>();

    /*@ModelAttribute
    public void findUserByPrincipal(Principal principal) {
        if (principal != null) {
            User user = getCacheUser(principal.getName()).orElseGet(() -> {
                Optional<User> opt = usersService.getUserByPrincipal(principal);
                if (opt.isPresent()) {
                    refreshCacheUser(redis, opt.get());
                }
                return opt.orElse(null);
            });
            if (user != null) {
                if (!user.isDisable()) {
                    local.set(user);
                    return;
                }
                throw HttpRequestException.newI18N("user.is.disable", user);
            }
        }
        local.remove();
    }*/

    public Optional<User> getCacheUser(String openid) {
        return Optional.ofNullable((User) redis.get("user:login:" + openid));
    }

   /* public static void refreshCacheUser(ValueOperations redis, User user) {
        redis.set("user:login:" + user.getUuid(), user, 1, TimeUnit.HOURS);
    }*/

    protected User getLoginUser() {
        User info = local.get();
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
