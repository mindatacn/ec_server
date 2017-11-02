package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<PtDepartment> findByCompanyIdAndNameLikeAndState(Integer companyId, String name, Integer state, Pageable
            pageable);

    Page<PtDepartment> findByNameLikeAndState(String name, Integer state, Pageable pageable);

    Page<PtDepartment> findByCompanyIdAndState(Integer companyId, Integer state, Pageable pageable);

    Page<PtDepartment> findByState(Integer state, Pageable pageable);
}
