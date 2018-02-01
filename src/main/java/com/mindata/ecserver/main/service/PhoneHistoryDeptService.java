package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtDepartmentManager;
import com.mindata.ecserver.main.manager.PtPhoneHistoryDeptManager;
import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.vo.PhoneHistoryDeptBeanVO;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/5.
 * 部门营销历史数据统计
 */
@Service
public class PhoneHistoryDeptService {
    @Resource
    private PtPhoneHistoryDeptManager ptPhoneHistoryDeptManager;
    @Resource
    private PtDepartmentManager ptDepartmentManager;

    public PhoneHistoryDeptBeanVO findDeptHistoryByDateBetween(Long deptId, String begin, String end, Pageable
            pageable) {
        if (deptId == null) {
            deptId = ShiroKit.getCurrentDeptId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        return findDeptHistoryByDateBetween(deptId, beginDate, endDate, pageable);
    }

    /**
     * 部门历史营销统计功能(某个部门某段时间的累计)
     */
    @SuppressWarnings("Duplicates")
    public PhoneHistoryDeptBeanVO findDeptHistoryByDateBetween(Long deptId, Date begin, Date end, Pageable
            pageable) {
        //这一段时间的累计数据
        List<Object[]> list = ptPhoneHistoryDeptManager.findTotalByDeptId(deptId, begin, end);
        PhoneHistoryDeptBeanVO deptBeanVO = new PhoneHistoryDeptBeanVO(list.get(0));
        deptBeanVO.setDeptName(ptDepartmentManager.findByDeptId(deptId).getName());
        deptBeanVO.setDeptId(deptId);
        return deptBeanVO;

        //sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
        //PhoneHistoryVO historyVO = new PhoneHistoryVO();
        //PhoneHistoryBeanVO totalVO = new PhoneHistoryBeanVO(list.get(0));

        //historyVO.setTotal(totalVO);
        //分页查询这段时间内的分页数据
        //Page<PtPhoneHistoryDept> page = ptPhoneHistoryDeptManager.findByDeptId(deptId, beginDate, endDate, pageable);
        //List<PhoneHistoryBeanVO> beanVOS = page.getContent().stream().map(ptPhoneHistoryDept -> {
        //    PhoneHistoryBeanVO vo = new PhoneHistoryBeanVO();
        //    vo.setPushCount(ptPhoneHistoryDept.getPushCount());
        //    vo.setTotalCallCount(ptPhoneHistoryDept.getTotalCallCount());
        //    vo.setValidCount(ptPhoneHistoryDept.getValidCount());
        //    vo.setTotalCallTime(ptPhoneHistoryDept.getTotalCallTime());
        //    vo.setTotalCustomer(ptPhoneHistoryDept.getTotalCustomer());
        //    return vo;
        //}).collect(Collectors.toList());
        //SimplePage<PhoneHistoryBeanVO> simplePage = new SimplePage<>(page.getTotalPages(), page.getTotalElements(),
        //        beanVOS);
        //historyVO.setPage(simplePage);

    }

    public List<PhoneHistoryDeptBeanVO> findDeptHisTotalByCompanyIdAndDateBetween(Long companyId, String begin,
                                                                                  String end,
                                                                                  Pageable pageable) {
        if (companyId == null) {
            companyId = ShiroKit.getCurrentCompanyId();
        }
        //获取该公司所有部门这段时间内的累计数据，以部门为单位分页
        List<PtDepartment> departments = ptDepartmentManager.findByCompanyIdAndState(companyId, 0);
        return departments.stream().map(ptDepartment -> findDeptHistoryByDateBetween(ptDepartment.getId(), begin, end,
                pageable)).collect(Collectors.toList());
    }
}
