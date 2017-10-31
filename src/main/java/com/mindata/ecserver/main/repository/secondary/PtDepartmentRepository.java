package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtDepartmentRepository extends JpaRepository<PtDepartment, Integer> {
    /**
     * 根据ecDeptId查询本地的Department
     *
     * @param deptId
     *         deptId
     * @return Department
     */
    PtDepartment findByEcDeptId(Integer deptId);
}
