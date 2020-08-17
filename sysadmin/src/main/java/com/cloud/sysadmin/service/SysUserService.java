package com.cloud.sysadmin.service;

import cn.hutool.core.date.DateUtil;
import com.cloud.common.base.base.AdminConstant;
import com.cloud.common.base.entity.SysUser;
import com.cloud.common.base.vo.Result;
import com.cloud.common.exception.HttpRequestException;
import com.cloud.common.util.ResultUtil;
import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class SysUserService {

    @Resource
    SysUserRepository userRepository;

    @Autowired
    EntityManager manager;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public Page<SysUser> findByCondition(SysUser user, SearchVo searchVo, Pageable pageable) {
        return userRepository.findAll(new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Path<Long> idField = root.get("id");
                Path<String> usernameField = root.get("username");
                Path<String> emailField = root.get("email");
                Path<Integer> statusField = root.get("status");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(String.valueOf(user.getId()))) {
                    list.add(cb.equal(idField, user.getId()));
                }
                //模糊搜素
                if (StringUtils.isNotBlank(user.getUsername())) {
                    list.add(cb.like(usernameField, '%' + user.getUsername() + '%'));
                }
                if (StringUtils.isNotBlank(user.getEmail())) {
                    list.add(cb.like(emailField, '%' + user.getEmail() + '%'));
                }
                //状态
                if (user.getStatus() >= 0) {
                    list.add(cb.equal(statusField, user.getStatus()));
                }
                //创建时间
                if (StringUtils.isNotBlank(searchVo.getStartDate()) && StringUtils.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }


    public Optional<SysUser> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void saveOrUpdate(SysUser user) {
        if (user.getId() > 0) {
            manager.merge(user);
        } else {
            if (checkUser(user.getUsername())) {
                user.setPassword("123456");//初始密码为123456
                String md5Password = new BCryptPasswordEncoder().encode(user.getPassword());
                user.setPassword(md5Password);
            }
            manager.persist(user);
        }
    }

    public boolean checkUser(String username) {
        Optional<SysUser> user = this.findByUsername(username);
        if (user.isPresent()) {
            return false;
        }
        return true;
    }

    public Result<Object> updatePassword(long id, String oldPassword, String newPassword) {
        SysUser sysUser = userRepository.findById(id).get();
        if (StringUtils.isNotBlank(oldPassword)) {
            if (!new BCryptPasswordEncoder().matches(oldPassword, sysUser.getPassword())) {
                throw HttpRequestException.newI18N("oldpassword.error");
            }
        }

        if (StringUtils.isBlank(newPassword)) {
            newPassword = AdminConstant.DEFULT_USER_PASSWORD;
        }
        sysUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        manager.merge(sysUser);
        return ResultUtil.success("success");
    }

    public Result<Object> delete(long id) {
        SysUser user = userRepository.findById(id).orElseThrow(() -> {
            return HttpRequestException.newI18N("not.find.sysuser");
        });
        user.setStatus(0);
        manager.merge(user);
        return ResultUtil.success("success");
    }


    public Result<Object> batchDelete(long[] ids) {
        for (long id : ids) {
            SysUser user = userRepository.findById(id).orElseThrow(() -> {
                return HttpRequestException.newI18N("not.find.sysuser");
            });
            user.setStatus(0);
            manager.merge(user);
        }
        return ResultUtil.success("success");
    }

}
