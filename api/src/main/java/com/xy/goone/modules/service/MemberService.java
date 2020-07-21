package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.MemberRepository;
import com.xy.goone.modules.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class MemberService {

    @Resource
    EntityManager manager;

    @Resource
    MemberRepository memberRepository;

    public void updateUser(Member member) {
        manager.merge(member);
    }

    public Optional<Member> findByPhone(String phone){
        return memberRepository.findByPhone(phone);
    }

    public void save(Member member){
        memberRepository.save(member);
    }

}
