package com.cloud.authorization.feign;


import com.cloud.authorization.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "sysadmin",url = "http://localhost:8002",fallback = UserFeignFallBack.class)
public interface UserFeign {

    @PostMapping(value = "/user/getByUsername/{name}")
    User getByUsername(@PathVariable String name);

    @PostMapping(value = "/user/getByPhone")
    User getByPhone(@PathVariable(value = "phone") String phone);





}
