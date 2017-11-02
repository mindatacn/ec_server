package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtDepartmentManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.vo.DepartmentSimpleVO;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
@Service
public class DepartmentService {
    @Resource
    private PtDepartmentManager ptDepartmentManager;
    @Resource
    private PtUserManager ptUserManager;

    /**
     * 根据部门名查询集合
     */
    public SimplePage<DepartmentSimpleVO> findByName(String name, Pageable pageable) {
        PtUser ptUser = ShiroKit.getCurrentUser();
        Page<PtDepartment> departmentPage = ptDepartmentManager.findByName(name, ptUser.getCompanyId(), 0, pageable);
        List<PtDepartment> departments = departmentPage.getContent();
        List<DepartmentSimpleVO> vos = new ArrayList<>(departments.size());
        for (PtDepartment department : departments) {
            DepartmentSimpleVO vo = new DepartmentSimpleVO();
            vo.setName(department.getName());
            vo.setCount(ptUserManager.countByDepartmentId(department.getId()));
            vo.setId(department.getId());
            String leaderStr = "";
            String leaders = department.getLeaders();
            if (!StrUtil.isEmpty(leaders)) {
                for (String str : leaders.split(",")) {
                    leaderStr += ptUserManager.findByUserId(Integer.valueOf(str)).getName() + " ";
                }
            }

            vo.setLeaders(leaderStr);
            vos.add(vo);
        }
        return new SimplePage<>(departmentPage.getTotalPages(), departmentPage.getTotalElements(), vos);
    }
}
