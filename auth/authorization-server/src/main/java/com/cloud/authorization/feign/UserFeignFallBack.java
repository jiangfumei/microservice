package com.cloud.authorization.feign;


import com.cloud.authorization.entity.User;
import com.cloud.authorization.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallBack implements UserFeign {

    @Override
    public UserEntity getByUsername(String username) {
        return new UserEntity();
    }

    @Override
    public UserEntity getByPhone(String phone) {
        return new UserEntity();
    }
}
