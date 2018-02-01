package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtDepartmentManager;
import com.mindata.ecserver.main.manager.PtPhoneHistoryUserManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.vo.PhoneHistoryUserBeanVO;
import com.xiaoleilu.hutool.date.DateUtil;
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
        vo.setDeptId(ptUser.getDepartmentId());
        vo.setUserId(userId);
        return vo;
    }

    private List<PhoneHistoryUserBeanVO> findPersonalHistoryByDeptId(Long deptId, Date begin, Date end) {
        List<PtUser> ptUsers = ptUserManager.findByDeptIdAndState(deptId, 0);
        return ptUsers.stream().map(ptUser -> findPersonalHistoryByUserId(ptUser.getId(), begin, end)).collect
                (Collectors.toList());
    }

    public List<PhoneHistoryUserBeanVO> findPersonalHistoryByCompanyId(Long companyId, Date begin, Date end) {
        List<PtDepartment> ptDepartments = ptDepartmentManager.findByCompanyIdAndState(companyId, 0);
        List<PhoneHistoryUserBeanVO> vos = new ArrayList<>();
        for (PtDepartment ptDepartment : ptDepartments) {
            vos.addAll(findPersonalHistoryByDeptId
                    (ptDepartment.getId(), begin, end));
        }
        return vos;
    }
}
