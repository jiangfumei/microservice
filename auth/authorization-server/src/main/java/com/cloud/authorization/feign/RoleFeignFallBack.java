package com.cloud.authorization.feign;

import com.cloud.authorization.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleFeignFallBack implements RoleFeign {

    @Override
    public List<Role> getByUserId(long id) {
        return null;
    }
}
