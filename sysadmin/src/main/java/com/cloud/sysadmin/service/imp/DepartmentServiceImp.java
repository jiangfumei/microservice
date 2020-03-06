package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.Department;
import com.cloud.sysadmin.repository.DepartMentRepository;
import com.cloud.sysadmin.service.DepartmentService;
import com.cloud.sysadmin.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class DepartmentServiceImp implements DepartmentService {

    @Resource
    DepartMentRepository departMentRepository;

    @Resource
    SecurityUtil securityUtil;

    @Resource
    EntityManager manager;

    @Override
    public List<Department> findByParentIdOrderBySortOrder(long parentId, boolean openDataFilter) {
        // 数据权限
        List<String> depIds = securityUtil.getDeparmentIds();
        if(depIds!=null&&depIds.size()>0&&openDataFilter){
            List<Long> lids = depIds.stream().map(Long::valueOf).collect(Collectors.toList());
            return departMentRepository.findByParentIdAndIdInOrderBySortOrder(parentId, lids);
        }
        return departMentRepository.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public Department findByParentId(long parentId) {
        return departMentRepository.findByParentId(parentId);
    }

    @Override
    public List<Department> findByParentIdAndStatusOrderBySortOrder(long parentId, int status) {
        return departMentRepository.findByParentIdAndStatusOrderBySortOrder(parentId,status);
    }

    @Override
    public void update(Department department) {
        manager.merge(department);
    }

    @Override
    public Department merge(Department department) {
        return manager.merge(department);
    }
}
