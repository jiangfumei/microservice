package com.cloud.authorization.feign;


import com.cloud.authorization.entity.Role;
import com.cloud.authorization.entity.UserEntity;
import com.cloud.common.vo.SearchVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class UserFeignFallBack implements UserFeign {

    @Override
    public UserEntity getByUsername(String username) {
        return new UserEntity();
    }

    @Override
    public UserEntity getByPhone(String phone) {
        return new UserEntity();
    }

    @Override
    public List<Role> getByUserId(SearchVo search) {
        return new ArrayList<>();
    }
}
