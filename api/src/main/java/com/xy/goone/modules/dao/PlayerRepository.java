package com.xy.goone.modules.dao;

import com.xy.goone.modules.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fumei.jiang
 * @date 2019-08-08 15:43
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("select p from Player p where p.name like %?1% and p.status=1")
    Page<Player> findByKey(String key, Pageable pageable);

    @Query("select p from Player p where p.status=?1")
    Page<Player> findAllByStatus(int status, Pageable pageable);

    Page<Player> findByStatus(int status,Pageable pageable);

    @Query(value = "select p from go_player p left join go_user_player up on p. = up.player_id where up.status = 1 and up.user_id = ?1",nativeQuery = true)
    Page<Player> getPlayerByStatus(int userId);


}
