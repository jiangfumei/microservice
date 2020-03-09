package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.RoleDepartment;
import com.cloud.sysadmin.repository.RoleDepartmentRepository;
import com.cloud.sysadmin.service.RoleDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RoleDepartmentServiceImp implements RoleDepartmentService {

    @Resource
    RoleDepartmentRepository roleDepartmentRepository;

    @Override
    public void deleteByDepartmentId(long depId) {
        roleDepartmentRepository.deleteByDepartmentId(depId);
    }

    @Override
    public void deleteByRoleId(long roleId) {
        roleDepartmentRepository.deleteByRoleId(roleId);
    }

    @Override
    public List<RoleDepartment> findByRoleId(long roleId) {
        return roleDepartmentRepository.findByRoleId(roleId);
    }
}
