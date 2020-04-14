package com.cloud.authorization.feign;


import com.cloud.authorization.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallBack implements UserFeign {

    @Override
    public User getByUsername(String username) {
        return new User();
    }

    @Override
    public User getByPhone(String phone) {
        return new User();
    }
}
