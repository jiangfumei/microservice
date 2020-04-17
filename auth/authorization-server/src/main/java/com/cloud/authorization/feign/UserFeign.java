package com.cloud.authorization.feign;


import com.cloud.authorization.entity.Role;
import com.cloud.authorization.entity.User;
import com.cloud.authorization.entity.UserEntity;
import com.cloud.common.vo.SearchVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(name = "sysadmin",url = "http://localhost:8002",fallback = UserFeignFallBack.class)
public interface UserFeign {

    @PostMapping(value = "/user/getByUsername/{name}")
    UserEntity getByUsername(@PathVariable String name);

    @PostMapping(value = "/role/getByUserId")
    List<Role> getByUserId(SearchVo search);

    @PostMapping(value = "/user/getByPhone")
    UserEntity getByPhone(@PathVariable(value = "phone") String phone);







}
