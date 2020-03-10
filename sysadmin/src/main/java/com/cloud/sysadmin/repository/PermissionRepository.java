package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long>, JpaSpecificationExecutor<Permission> {

    List<Permission> findByTitle(String title);

    List<Permission> findByTypeAndStatusOrderBySortOrder(int type,int status);

    List<Permission> findByLevelOrderBySortOrder(int level);

    List<Permission> findByParentIdOrderBySortOrder(long parentId);

    List<Permission> findByTitleLikeOrderBySortOrder(String title);

   /* @Query("SELECT DISTINCT p.id, p.name, p.showAlways, p.title, p.path, p.icon, p.type, p.component, p.level,\n" +
            "        p.buttonType, p.parentId, p.sortOrder, p.description, p.url\n" +
            "      FROM User u\n" +
            "      LEFT JOIN UserRole ur ON u.id = ur.userId\n" +
            "      LEFT JOIN RolePermission rp ON ur.roleId = rp.roleId\n" +
            "      LEFT JOIN Permission p ON p.id = rp.permissionId\n" +
            "      WHERE u.id = #{userId}\n" +
            "      ORDER BY p.sortOrder ASC")*/

    @Query(value = "select distinct p.id, p.name, p.show_always, p.title, p.path, p.icon, p.type, p.component, p.level,p.button_type," +
            "p.parent_id, p.sort_order, p.description, p.url from user u left join user_role ur on u.id = ur.user_id" +
            "left join role_permission  rp on ur.role_id = rp.role_id left join permission p on p.id = rp.permission_id" +
            "where u.id =?1 order by p.sort_order asc",nativeQuery = true)
    List<Permission> findByUserId(long userId);
}
