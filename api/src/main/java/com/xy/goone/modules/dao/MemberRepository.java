package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByEmailAndPassword(String email, String password);

    Member findByPhoneAndStatus(String phone, int status);

    Member findByNickNameAndStatus(String nickName, int status);

}
