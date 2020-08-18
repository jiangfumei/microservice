package com.cloud.common.feign;


import com.cloud.common.base.entity.LoginAppUser;
import com.cloud.common.base.entity.SysRole;
import com.cloud.common.base.entity.SysUser;
import com.cloud.common.constant.ServiceNameConstants;
import com.cloud.common.feign.fallback.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mall
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {
    /**
     * feign rpc访问远程/users/{username}接口
     * 查询用户实体对象SysUser
     *
     * @param username
     * @return
     */
   /* @GetMapping(value = "/name/{username}")
    SysUser findByUsername(@PathVariable("username") String username);
*/
    /**
     * feign rpc访问远程/users-anon/login接口
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/name/{username}", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/users-anon/mobile", params = "mobile")
    LoginAppUser findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/users-anon/openId", params = "openId")
    LoginAppUser findByOpenId(@RequestParam("openId") String openId);

    @GetMapping(value = "/users-anon/id", params = "id")
    LoginAppUser findById(Long id);

    @GetMapping("/users/getRolesByUserId")
    List<SysRole> getRolesByUserId(@RequestParam("id") Long id);


}
