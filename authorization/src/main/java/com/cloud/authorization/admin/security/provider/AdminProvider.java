package com.cloud.authorization.admin.security.provider;

import com.cloud.sysadmin.entity.Permission;
import com.cloud.sysadmin.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "sysadmin",fallback = AdminProviderFallBack.class)
public interface AdminProvider {

    @PostMapping(value = "/permission/security")
    List<Permission> findByTypeAndStatusOrderBySortOrder(@PathVariable int type,@PathVariable int status);

    @PostMapping(value = "/user/getByUsername")
    User getByUsername(@PathVariable String name);





}
