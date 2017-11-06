package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRepository extends JpaRepository<PtUser, Integer> {
    PtUser findByAccount(String account);

    /**
     * 根据ecUserId查询
     *
     * @param ecUserId
     *         ecuserId
     * @return 本地user
     */
    PtUser findByEcUserId(Long ecUserId);

    /**
     * 查询某个部门的所有成员数量
     */
    Integer countByDepartmentIdAndState(Integer departmentId, Integer state);

    List<PtUser> findByDepartmentIdAndState(Integer departmentId, Integer state);

    List<PtUser> findByCompanyIdAndState(Integer companyId, Integer state);

    /**
     * 根据名字模糊查询某个部门的员工
     *
     * @param deptId
     *         部门id
     * @param state
     *         员工状态
     * @param name
     *         名字
     * @return 集合
     */
    List<PtUser> findByDepartmentIdAndStateAndNameLike(Integer deptId, Integer state, String name);
}
