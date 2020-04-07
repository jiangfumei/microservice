package com.xy.goone.modules.service;

import com.xy.goone.modules.dao.MemberRepository;
import com.xy.goone.modules.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

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

    public Member findByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }


    public Member findByEmailAndStatus(String email, int status) {
        return memberRepository.findByEmailAndStatus(email, status);
    }

}
