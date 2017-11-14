package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtDepartmentRepository extends JpaRepository<PtDepartment, Long> {
    /**
     * 根据ecDeptId查询本地的Department
     *
     * @param deptId
     *         deptId
     * @return Department
     */
    PtDepartment findByEcDeptId(Long deptId);

    Page<PtDepartment> findByCompanyIdAndNameLikeAndState(Long companyId, String name, Integer state, Pageable
            pageable);

    Page<PtDepartment> findByNameLikeAndState(String name, Integer state, Pageable pageable);

    Page<PtDepartment> findByCompanyIdAndState(Long companyId, Integer state, Pageable pageable);

    Page<PtDepartment> findByState(Integer state, Pageable pageable);

    List<PtDepartment> findByCompanyIdAndState(Long companyId, Integer state);
}
