package com.cloud.sysadmin.service;

import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    List<User> findByDepartmentId(long departmentId);

    User update(User user);

    User findByUsername(String username);

    /**
     * 多条件分页获取用户
     * @param user
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<User> findByCondition(User user, SearchVo searchVo, Pageable pageable);

    User findByPhone(String phone);

}
