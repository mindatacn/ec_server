package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CompanyDeptBean;
import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.repository.secondary.PtDepartmentRepository;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/31.
 */
@Service
public class PtDepartmentManager extends BaseService {
    @Resource
    private PtDepartmentRepository departmentRepository;
    @Resource
    private PtCompanyManager companyManager;

    public PtDepartment findByEcDeptId(Integer ecDeptId) {
        return departmentRepository.findByEcDeptId(ecDeptId);
    }

    public PtDepartment findByDeptId(Integer id) {
        return departmentRepository.findOne(id);
    }

    /**
     * 根据Ec的部门数据同步到本地
     *
     * @param companyDeptBean
     *         ec的dept结构
     * @return 本地的部门数据
     */
    public PtDepartment add(CompanyDeptBean companyDeptBean, Integer companyId) {
        //deptId是唯一的
        PtDepartment ptDepartment = findByEcDeptId(companyDeptBean.getDeptId());
        if (ptDepartment != null) {
            return ptDepartment;
        }
        ptDepartment = new PtDepartment();
        ptDepartment.setEcDeptId(companyDeptBean.getDeptId());
        ptDepartment.setName(companyDeptBean.getDeptName());
        ptDepartment.setEcParentDeptId(companyDeptBean.getParentDeptId());
        ptDepartment.setCompanyId(companyId);
        ptDepartment.setThreshold(companyManager.findOne(companyId).getThreshold());
        ptDepartment.setSort(companyDeptBean.getSort());
        ptDepartment.setCreateTime(CommonUtil.getNow());
        ptDepartment.setUpdateTime(CommonUtil.getNow());
        ptDepartment.setMemo("");
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
    public List<PtDepartment> addDepts(List<CompanyDeptBean> companyDeptBeans, Integer companyId) {
        List<PtDepartment> ptDepartments = new ArrayList<>();
        for (CompanyDeptBean bean : companyDeptBeans) {
            ptDepartments.add(add(bean, companyId));
        }
        //然后根据ec的父部门信息，设置本地的父部门信息
        for (PtDepartment department : ptDepartments) {
            //取到本地的ec父部门信息
            Integer ecParentDeptId = department.getEcParentDeptId();
            //如果为0，说明是顶级菜单
            if (ecParentDeptId.equals(0)) {
                department.setParentId(0);
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
