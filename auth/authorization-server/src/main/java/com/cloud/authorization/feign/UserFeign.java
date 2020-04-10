package com.cloud.authorization.feign;


import com.cloud.authorization.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "sysadmin",fallback = UserFeignFallBack.class)
public interface UserFeign {

    @PostMapping(value = "/user/getByUsername")
    User getByUsername(@PathVariable(value = "name") String name);

    @PostMapping(value = "/user/getByPhone")
    User getByPhone(@PathVariable(value = "phone") String phone);





}
