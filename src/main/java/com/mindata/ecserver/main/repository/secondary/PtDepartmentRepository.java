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

    /***
     * 查询某公司的部门
     * @param companyId
     *   companyId
     * @param name
     * name
     * @param state
     * state
     * @param pageable
     * pageable
     * @return
     * 分页结果集
     */
    Page<PtDepartment> findByCompanyIdAndNameLikeAndState(Long companyId, String name, Integer state, Pageable
            pageable);

    /**
     * 按名称模糊查询
     *
     * @param name
     *         name
     * @param state
     *         state
     * @param pageable
     *         pageable
     * @return 分页结果集
     */
    Page<PtDepartment> findByNameLikeAndState(String name, Integer state, Pageable pageable);

    /**
     * 按公司查询
     *
     * @param companyId
     *         companyId
     * @param state
     *         state
     * @param pageable
     *         pageable
     * @return 分页结果集
     */
    Page<PtDepartment> findByCompanyIdAndState(Long companyId, Integer state, Pageable pageable);

    /**
     * 按状态查询
     *
     * @param state
     *         state
     * @param pageable
     *         pageable
     * @return 分页结果集
     */
    Page<PtDepartment> findByState(Integer state, Pageable pageable);

    /**
     * 按公司和状态查询
     *
     * @param companyId
     *         companyId
     * @param state
     *         state
     * @return 分页结果集
     */
    List<PtDepartment> findByCompanyIdAndState(Long companyId, Integer state);
}
