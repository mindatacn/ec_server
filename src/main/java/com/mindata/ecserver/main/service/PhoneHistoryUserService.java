package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtDepartmentManager;
import com.mindata.ecserver.main.manager.PtPhoneHistoryUserManager;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryUser;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.vo.PhoneHistoryUserBeanVO;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/5.
 */
@Service
public class PhoneHistoryUserService {
    @Resource
    private PtPhoneHistoryUserManager ptPhoneHistoryUserManager;
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtDepartmentManager ptDepartmentManager;
    @Resource
    private PtRoleManager ptRoleManager;

    /**
     * 个人历史营销统计功能
     */
    public List<PhoneHistoryUserBeanVO> findPersonalHistoryByDate(Long deptId, String begin, String end) {
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        //如果当前用户不是管理员
        PtUser ptUser = ShiroKit.getCurrentUser();
        if (deptId != null) {
            return findPersonalHistoryByDeptId(deptId, beginDate, endDate);
        }
        //查整个公司
        return findPersonalHistoryByCompanyId(ptUser.getCompanyId(), beginDate, endDate);
        //List<PtRole> roles = ptRoleManager.findByUserId(ptUser.getId());
        //boolean manager = false;
        //for (PtRole role : roles) {
        //    if (Constant.ROLE_MANAGER.equals(role.getName())) {
        //        manager = true;
        //        break;
        //    }
        //}
        //if (!manager) {
        //    deptId = ptUser.getDepartmentId();
        //}

    }

    public PhoneHistoryUserBeanVO findPersonalHistoryByUserId(Long userId, String begin, String end) {
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        return findPersonalHistoryByUserId(userId, beginDate, endDate);
    }

    private PhoneHistoryUserBeanVO findPersonalHistoryByUserId(Long userId, Date begin, Date end) {
        List<Object[]> list = ptPhoneHistoryUserManager.findTotalByUserId(userId, begin, end);
        PhoneHistoryUserBeanVO vo = new PhoneHistoryUserBeanVO(list.get(0));
        PtUser ptUser = ptUserManager.findByUserId(userId);
        vo.setDeptName(ptDepartmentManager.findByDeptId(ptUser.getDepartmentId()).getName());
        vo.setUserName(ptUser.getName());
        return vo;
    }

    private List<PhoneHistoryUserBeanVO> findPersonalHistoryByDeptId(Long deptId, Date begin, Date end) {
        List<PtUser> ptUsers = ptUserManager.findByDeptIdAndState(deptId, 0);
        return ptUsers.stream().map(ptUser -> findPersonalHistoryByUserId(ptUser.getId(), begin, end)).collect
                (Collectors.toList());
    }

    private List<PhoneHistoryUserBeanVO> findPersonalHistoryByCompanyId(Long companyId, Date begin, Date end) {
        List<PtDepartment> ptDepartments = ptDepartmentManager.findByCompanyIdAndState(companyId, 0);
        List<PhoneHistoryUserBeanVO> vos = new ArrayList<>();
        for (PtDepartment ptDepartment : ptDepartments) {
            vos.addAll(findPersonalHistoryByDeptId
                    (ptDepartment.getId(), begin, end));
        }
        return vos;
    }

    private Page<PtPhoneHistoryUser> findByUserIdAndDate(Long userId, Date begin, Date end, Pageable pageable) {
        //分页查询这段时间内的分页数据
        return ptPhoneHistoryUserManager.findByUserId(userId, begin, end, pageable);
        //List<PhoneHistoryBeanVO> beanVOS = page.getContent().stream().map(ptPhoneHistoryUser -> {
        //    PhoneHistoryBeanVO vo = new PhoneHistoryBeanVO();
        //    vo.setPushCount(ptPhoneHistoryUser.getPushCount());
        //    vo.setTotalCallCount(ptPhoneHistoryUser.getTotalCallCount());
        //    vo.setValidCount(ptPhoneHistoryUser.getValidCount());
        //    vo.setTotalCallTime(ptPhoneHistoryUser.getTotalCallTime());
        //    vo.setTotalCustomer(ptPhoneHistoryUser.getTotalCustomer());
        //    return vo;
        //}).collect(Collectors.toList());
        //SimplePage<PhoneHistoryBeanVO> simplePage = new SimplePage<>(page.getTotalPages(), page.getTotalElements(),
        //        beanVOS);
    }
}
