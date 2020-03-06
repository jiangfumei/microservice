package com.cloud.sysadmin.repository;

import com.cloud.sysadmin.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartMentRepository extends JpaRepository<Department,Long>, JpaSpecificationExecutor<Department> {

    List<Department> findByParentIdOrderBySortOrder(long parentId);
    /**
     * 通过父id获取 升序 数据权限
     * @param parentId
     * @param departmentIds
     * @return
     */
    @Query("select d from Department d where d.id in (:departmentIds)")
    List<Department> findByParentIdAndIdInOrderBySortOrder(long parentId, List<Long> departmentIds);

    Department findByParentId(long id);


    @Query("select dep from Department dep where dep.parentId=?1 and dep.status=?2 order by dep.sortOrder")
    List<Department> findByParentIdAndStatusOrderBySortOrder(long id, int status);

}
