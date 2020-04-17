package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {

    //List<Role> findByDefaultRole(Boolean defaultRole);

/*
    @Query("select r.* from role r left join user_role ur where r.user.id = ?1 on ur.userId = r.user.id")
*/
    @Query(nativeQuery = true,value = "select r.* from role r , user_role ur where ur.user_id = ?1 on ur.user_id = r.user.id")
    List<Role> getByUserId(long id);
}
