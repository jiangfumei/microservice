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

    List<Department> findByParentIdAndIdInOrderBySortOrder(long parentId, List<Long> departmentIds);

    Department findByParentId(long id);

    List<Department> findByParentIdAndStatusOrderBySortOrder(long id, int status);

    /**
     * 部门名模糊搜索 升序 数据权限
     * @param title
     * @param departmentIds
     * @return
     */
    List<Department> findByTitleLikeAndIdInOrderBySortOrder(String title, List<String> departmentIds);

    List<Department> findByTitleLikeOrderBySortOrder(String title);

}
