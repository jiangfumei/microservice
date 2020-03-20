package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.UserPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPlayerRepository extends JpaRepository<UserPlayer,Integer> {

   /* @Query("select up from UserPlayer up where up.userId=?1 and up.status=?2")
    List<UserPlayer> findByUserId(int userId, int status);

    @Query("select up from UserPlayer  up where up.userId=?1 and up.palyerId=?2 and up.status=?3")
    UserPlayer findByUserIdAndPlayerId(int userId, int playerId, int status);

    @Query("select up from UserPlayer  up where up.userId=?1 and up.palyerId=?2")
    UserPlayer findByUserIdAndPlayer(int userId, int playerId);

    List<UserPlayer> findByUserIdAndStatus(int userId, int status);
*/

   Page<UserPlayer> findByUserIdAndStatus(int userId, int status, Pageable pageable);
}
