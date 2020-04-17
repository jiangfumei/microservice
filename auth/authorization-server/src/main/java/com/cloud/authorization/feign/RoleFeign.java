package com.cloud.authorization.feign;

import com.cloud.authorization.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
@FeignClient(name = "sysadmin",url = "http://localhost:8002",fallback = RoleFeignFallBack.class)
*/
public interface RoleFeign {

    @GetMapping(value = "/role/getByRoleId/{id}")
    List<Role> getByUserId(@PathVariable long id);
}
