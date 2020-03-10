package com.cloud.sysadmin.service.imp;

import cn.hutool.core.date.DateUtil;
import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.repository.UserRepository;
import com.cloud.sysadmin.service.UserService;
import com.cloud.sysadmin.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@Transactional
public class UserServiceImp implements UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    EntityManager manager;

    @Resource
    SecurityUtil securityUtil;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findByDepartmentId(long departmentId) {
        return userRepository.findByDepartmentId(departmentId);
    }

    @Override
    public User update(User user) {
        return manager.merge(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findByCondition(User user, SearchVo searchVo, Pageable pageable) {
        return userRepository.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> usernameField = root.get("username");
                //Path<String> mobileField = root.get("mobile");
                Path<String> emailField = root.get("email");
                Path<String> departmentIdField = root.get("departmentId");
                Path<String> sexField=root.get("sex");
                Path<Integer> typeField=root.get("type");
                Path<Integer> statusField=root.get("status");
                Path<Date> createTimeField=root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if(StringUtils.isNotBlank(user.getUsername())){
                    list.add(cb.like(usernameField,'%'+user.getUsername()+'%'));
                }

                if(StringUtils.isNotBlank(user.getEmail())){
                    list.add(cb.like(emailField,'%'+user.getEmail()+'%'));
                }

                //部门
                if(StringUtils.isNotBlank(String.valueOf(user.getDepartmentId()))) {
                    list.add(cb.equal(departmentIdField, user.getDepartmentId()));
                }

                //性别
                if(StringUtils.isNotBlank(user.getSex())){
                    list.add(cb.equal(sexField, user.getSex()));
                }
                //类型
                if(user.getType()!=null){
                    list.add(cb.equal(typeField, user.getType()));
                }
                //状态
                if(user.getStatus()!=null){
                    list.add(cb.equal(statusField, user.getStatus()));
                }
                //创建时间
                if(StringUtils.isNotBlank(searchVo.getStartDate())&&StringUtils.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                //数据权限
                List<String> depIds = securityUtil.getDeparmentIds();
                if(depIds!=null&&depIds.size()>0){
                    list.add(departmentIdField.in(depIds));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
}
