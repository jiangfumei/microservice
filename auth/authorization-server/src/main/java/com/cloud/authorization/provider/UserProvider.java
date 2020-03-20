package com.cloud.authorization.provider;


import com.cloud.authorization.entity.Permission;
import com.cloud.authorization.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "sysadmin",fallback = UserProviderFallBack.class)
public interface UserProvider {

    @PostMapping(value = "/permission/security")
    List<Permission> findByTypeAndStatusOrderBySortOrder(@PathVariable int type, @PathVariable int status);

    @PostMapping(value = "/user/getByUsername")
    User getByUsername(@PathVariable String name);

    @PostMapping(value = "/user/getByPhone")
    User getByPhone(@PathVariable String phone);





}
