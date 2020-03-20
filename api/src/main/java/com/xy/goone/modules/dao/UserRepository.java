package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fumei.jiang
 * @date 2019-07-29 17:02
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    @Query("select u from User u where u.email=?1 and u.status=?2")
    User findByEmailAndStatus(String email, int status);

    User findByNickNameAndStatus(String nickName, int status);

    User findByAvatarAndStatus(String nickName, int status);

}
