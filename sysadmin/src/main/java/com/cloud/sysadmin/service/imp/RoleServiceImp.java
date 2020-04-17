package com.cloud.sysadmin.service.imp;

import com.cloud.common.vo.SearchVo;
import com.cloud.sysadmin.entity.Role;
import com.cloud.sysadmin.entity.User;
import com.cloud.sysadmin.repository.RoleRepository;
import com.cloud.sysadmin.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
public class RoleServiceImp implements RoleService {

    @Resource
    RoleRepository roleRepository;

    @Resource
    EntityManager manager;

    /*@Override
    public List<Role> findByDefaultRole(Boolean defaultRole) {
        return roleRepository.findByDefaultRole(defaultRole);
    }
*/
    @Override
    public Role update(Role role) {
        return manager.merge(role);
    }

    @Override
    public List<Role> findByCondition(SearchVo searchVo) {
        return roleRepository.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //ListJoin<Role, User> orgHospReportJoin = root.join(root.getModel().getDeclaredList("users", User.class), JoinType.LEFT);
                Join<Role, User> roleJoin = root.join("users", JoinType.LEFT);
                predicates.add(cb.equal(roleJoin.get("id").as(Long.class), searchVo.getId()));
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
    });

    }

}
