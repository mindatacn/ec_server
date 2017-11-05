package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtDepartment;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryDept;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryDeptRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class PtPhoneHistoryDeptManager {
    @Resource
    private PtPhoneHistoryDeptRepository ptPhoneHistoryDeptRepository;
    @Resource
    private PtDepartmentManager ptDepartmentManager;
    @Resource
    private PtPhoneHistoryUserManager ptPhoneHistoryUserManager;

    /**
     * 查询某天某个公司所有部门的电话统计之和
     *
     * @param companyId
     *         公司id
     * @param begin
     *         一天开始时间
     * @param end
     *         一天结束时间
     * @return 数量集合
     */
    public List<Object[]> findCompanyOneDayTotalByCompanyId(Integer companyId, Date begin, Date end) throws
            IOException {
        //找到公司所有正常的部门
        List<PtDepartment> departments = ptDepartmentManager.findByCompanyIdAndState(companyId, 0);
        List<Integer> ids = departments.stream().map(PtDepartment::getId).collect(Collectors.toList());

        for (Integer deptId : ids) {
            Integer count = ptPhoneHistoryDeptRepository.countByDeptIdAndStartTimeBetween(deptId, begin, end);
            //如果今天没值，就去统计userHistory的总数量，并且把今天的值给补上
            if (count == 0) {
                //得到该部门所有员工今天的累计数据
                List<Object[]> userTotal = ptPhoneHistoryUserManager.findDeptOneDayTotalByDeptId(deptId, begin, end);
                Object[] objects = userTotal.get(0);
                PtPhoneHistoryDept historyDept = new PtPhoneHistoryDept();
                historyDept.setDeptId(deptId);
                historyDept.setStartTime(begin);
                historyDept.setTotalCallTime(CommonUtil.parseObject(objects[0]));
                historyDept.setTotalCallCount(CommonUtil.parseObject(objects[1]));
                historyDept.setTotalCustomer(CommonUtil.parseObject(objects[2]));
                historyDept.setPushCount(CommonUtil.parseObject(objects[3]));
                historyDept.setCreateTime(CommonUtil.getNow());
                historyDept.setUpdateTime(CommonUtil.getNow());
                ptPhoneHistoryDeptRepository.save(historyDept);
            }
        }

        //得到该天的累计数量
        return ptPhoneHistoryDeptRepository.findCount(ids, begin, end);
    }

}
