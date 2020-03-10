package com.cloud.sysadmin.service.imp;

import com.cloud.sysadmin.entity.DictData;
import com.cloud.sysadmin.repository.DictDataRepository;
import com.cloud.sysadmin.service.DictDataService;
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
import java.util.List;

@Slf4j
@Service
@Transactional
public class DictDataServiceImp implements DictDataService {

    @Resource
    DictDataRepository dictDataRepository;

    @Resource
    EntityManager entityManager;

    @Override
    public void deleteByDictId(long dictId) {
        dictDataRepository.deleteByDictId(dictId);
    }

    @Override
    public List<DictData> findByDictId(long dictId) {
        return dictDataRepository.findByDictId(dictId);
    }

    @Override
    public void update(DictData dictData) {
        entityManager.merge(dictData);
    }

    @Override
    public Page<DictData> findByCondition(DictData dictData, Pageable pageable) {
        return dictDataRepository.findAll(new Specification<DictData>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<DictData> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> titleField = root.get("title");
                Path<Integer> statusField = root.get("status");
                Path<String> dictIdField = root.get("dictId");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if(StringUtils.isNotBlank(dictData.getTitle())){
                    list.add(cb.like(titleField,'%'+dictData.getTitle()+'%'));
                }

                //状态
                if(dictData.getStatus()!=null){
                    list.add(cb.equal(statusField, dictData.getStatus()));
                }

                //所属字典
                if(StringUtils.isNotBlank(String.valueOf(dictData.getDictId()))){
                    list.add(cb.equal(dictIdField, dictData.getDictId()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
}
