package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.DepartmentHeader;
import com.cloud.sysadmin.repository.DepartmentHeaderRepository;
import com.cloud.sysadmin.service.DepartmentHeaderService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DepartmentHeaderServiceImp implements DepartmentHeaderService {

    @Resource
    DepartmentHeaderRepository headerRepository;

    @Override
    public List<Long> findHeaderByDepartmentId(long departmentId, Integer type) {
        List<Long> list = new ArrayList<>();
        List<DepartmentHeader> headers = headerRepository.findByDepartmentIdAndType(departmentId, type);
        headers.forEach(e->{
            list.add(e.getUserId());
        });
        return list;
    }

    @Override
    public void deleteByDepartmentId(long depId) {
        headerRepository.deleteByDepartmentId(depId);
    }

    @Override
    public void deleteByUserId(long userId) {
        headerRepository.deleteByUserId(userId);
    }
}
