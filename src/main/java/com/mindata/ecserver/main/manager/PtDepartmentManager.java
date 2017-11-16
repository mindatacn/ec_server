package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CompanyDeptBean;
import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.repository.secondary.PtDepartmentRepository;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.constant.Constant.STATE_NORMAL;

/**
 * @author wuweifeng wrote on 2017/10/31.
 */
@Service
public class PtDepartmentManager extends BaseService {
    @Resource
    private PtDepartmentRepository departmentRepository;
    @Resource
    private PtCompanyManager companyManager;

    public PtDepartment findByEcDeptId(Long ecDeptId) {
        return departmentRepository.findByEcDeptId(ecDeptId);
    }

    public PtDepartment findByDeptId(Long id) {
        return departmentRepository.findOne(id);
    }

    public List<PtDepartment> findByCompanyIdAndState(Long companyId, Integer state) {
        return departmentRepository.findByCompanyIdAndState(companyId, state);
    }

    /**
     * 根据名字查询
     *
     * @param name
     *         部门名称
     * @return 集合
     */
    public Page<PtDepartment> findByName(String name, Long companyId, Integer state, Pageable pageable) {
        Page<PtDepartment> departments;
        if (StrUtil.isEmpty(name)) {
            if (companyId == 0) {
                departments = departmentRepository.findByState(STATE_NORMAL, pageable);
            } else {
                departments = departmentRepository.findByCompanyIdAndState(companyId, STATE_NORMAL, pageable);
            }
        } else {
            if (companyId == 0) {
                departments = departmentRepository.findByNameLikeAndState("%" + name + "%", state, pageable);
            } else {
                departments = departmentRepository.findByCompanyIdAndNameLikeAndState(companyId, "%" + name + "%",
                        state, pageable);
            }
        }
        return departments;
    }

    /**
     * 根据Ec的部门数据同步到本地
     *
     * @param companyDeptBean
     *         ec的dept结构
     * @return 本地的部门数据
     */
    public PtDepartment add(CompanyDeptBean companyDeptBean, Long companyId, boolean force) {
        //deptId是唯一的
        PtDepartment ptDepartment = findByEcDeptId(companyDeptBean.getDeptId());
        if (ptDepartment != null && !force) {
            return ptDepartment;
        }
        if (ptDepartment == null) {
            ptDepartment = new PtDepartment();
            ptDepartment.setState(STATE_NORMAL);
            ptDepartment.setMemo("");
            ptDepartment.setThreshold(companyManager.findOne(companyId).getThreshold());
            ptDepartment.setCompanyId(companyId);
        }
        ptDepartment.setEcDeptId(companyDeptBean.getDeptId());
        ptDepartment.setName(companyDeptBean.getDeptName());
        ptDepartment.setEcParentDeptId(companyDeptBean.getParentDeptId());
        ptDepartment.setSort(companyDeptBean.getSort());
        ptDepartment.setCreateTime(CommonUtil.getNow());
        ptDepartment.setUpdateTime(CommonUtil.getNow());

        return departmentRepository.save(ptDepartment);
    }

    public PtDepartment addOrUpdate(PtDepartment ptDepartment) {
        return departmentRepository.save(ptDepartment);
    }

    /**
     * 从EC同步一批部门信息
     *
     * @param companyDeptBeans
     *         一批部门信息
     */
    public List<PtDepartment> addDepts(List<CompanyDeptBean> companyDeptBeans, Long companyId, boolean force) {
        List<PtDepartment> ptDepartments = companyDeptBeans.stream().map(companyDeptBean -> add(companyDeptBean,
                companyId, force)).collect(Collectors
                .toList());
        //然后根据ec的父部门信息，设置本地的父部门信息
        for (PtDepartment department : ptDepartments) {
            //取到本地的ec父部门信息
            Long ecParentDeptId = department.getEcParentDeptId();
            //如果为0，说明是顶级菜单
            if (ecParentDeptId.equals(0L)) {
                department.setParentId(0L);
                addOrUpdate(department);
                continue;
            }
            for (PtDepartment temp : ptDepartments) {
                if (temp.getEcDeptId().equals(ecParentDeptId)) {
                    department.setParentId(temp.getId());
                    department.setUpdateTime(CommonUtil.getNow());
                    addOrUpdate(department);
                    break;
                }
            }
        }

        return ptDepartments;
    }

}
