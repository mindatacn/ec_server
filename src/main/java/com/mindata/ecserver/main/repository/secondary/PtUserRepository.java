package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRepository extends JpaRepository<PtUser, Long> {
    PtUser findByAccount(String account);

    /**
     * 根据ecUserId查询
     *
     * @param ecUserId ecUserId
     * @return 本地user
     */
    PtUser findByEcUserId(Long ecUserId);

    /**
     * 查询某个部门的所有成员数量
     */
    Integer countByDepartmentIdAndState(Long departmentId, Integer state);

    /**
     * 查询某个部门所有正常状态员工
     *
     * @param departmentId 部门id
     * @param state        状态
     * @return 结果集
     */
    List<PtUser> findByDepartmentIdAndState(Long departmentId, Integer state);

    List<PtUser> findByCompanyIdAndState(Long companyId, Integer state);

    List<PtUser> findByCompanyIdAndStateAndNameLike(Long companyId, Integer state, String name);

    /**
     * 根据名字模糊查询某个部门的员工
     *
     * @param deptId 部门id
     * @param state  员工状态
     * @param name   名字
     * @return 集合
     */
    List<PtUser> findByDepartmentIdAndStateAndNameLike(Long deptId, Integer state, String name);

    /**
     * 根据状态和名字模糊查询
     *
     * @param state 是否可用
     * @param name  名字
     * @return 结果集
     */
    List<PtUser> findByStateAndNameLike(Integer state, String name);

    /**
     * 根据状态查询所有
     *
     * @param state 状态
     * @return 结果
     */
    List<PtUser> findByState(Integer state);

    /**
     * 查询最大id值
     *
     * @return 结果
     */
    @Query(value = "select max(id) from PtUser ")
    Long findMaxId();

    /**
     * 查找最新的用户信息
     * @param beginId 开始id
     * @param endId 结束id
     * @param pageable 分页
     * @return 结果
     */
    Page<PtUser> findByIdBetweenAndCompanyId(Long beginId, Long endId,Long companyId, Pageable pageable);

}
