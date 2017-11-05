package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryCompanyRepository;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 查询某公司某段时间内的历史通话统计信息
 *
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class PtPhoneHistoryCompanyManager {
    @Resource
    private PtPhoneHistoryCompanyRepository ptPhoneHistoryCompanyRepository;
    @Resource
    private PtPhoneHistoryDeptManager ptPhoneHistoryDeptManager;

    /**
     * 查询某段时间内某公司的通话统计信息
     *
     * @param companyId
     *         公司id
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @param pageable
     *         分页
     * @return 分页结果
     */
    public Page<PtPhoneHistoryCompany> findHistoryByDate(Integer companyId, Date begin, Date end, Pageable pageable)
            throws IOException {
        Date tempBegin = DateUtil.beginOfDay(begin);
        Date tempEnd = DateUtil.endOfDay(end);
        //查看间隔多少天
        long betweenDay = DateUtil.between(tempBegin, tempEnd, DateUnit.DAY);
        //获取表中该时间段的统计数据集合
        Integer count = ptPhoneHistoryCompanyRepository.countByCompanyIdAndStartTimeBetween(companyId, tempBegin,
                tempEnd);
        //间隔数量应该=数据库数量-1，譬如开始和结束都是今天，数据会有一条数据
        if (count == betweenDay + 1) {
            //数量正确，说明每天都有值，那么就返回数据库数据
            return ptPhoneHistoryCompanyRepository.findByCompanyIdAndStartTimeBetween
                    (companyId,
                    begin, end, pageable);
        }
        //如果数量对不上，就要去看哪天缺失，并且补上
        for (; tempBegin.before(tempEnd); tempBegin = DateUtil.offsetDay(tempBegin, 1)) {
            Date oneDayEnd = DateUtil.endOfDay(tempBegin);
            //每一天的
            Integer oneDayCount = ptPhoneHistoryCompanyRepository.countByCompanyIdAndStartTimeBetween(companyId,
                    tempBegin, oneDayEnd);
            if (oneDayCount == 0) {
                //从dept表计算总量，得到今天的数据
                List<Object[]> deptTotal = ptPhoneHistoryDeptManager.findCompanyOneDayTotalByCompanyId(companyId,
                        tempBegin, oneDayEnd);
                Object[] objects = deptTotal.get(0);
                PtPhoneHistoryCompany historyCompany = new PtPhoneHistoryCompany();
                historyCompany.setCompanyId(companyId);
                historyCompany.setStartTime(tempBegin);
                historyCompany.setTotalCallTime(CommonUtil.parseObject(objects[0]));
                historyCompany.setTotalCallCount(CommonUtil.parseObject(objects[1]));
                historyCompany.setTotalCustomer(CommonUtil.parseObject(objects[2]));
                historyCompany.setPushCount(CommonUtil.parseObject(objects[3]));
                historyCompany.setValidCount(CommonUtil.parseObject(objects[4]));
                historyCompany.setCreateTime(CommonUtil.getNow());
                historyCompany.setUpdateTime(CommonUtil.getNow());
                ptPhoneHistoryCompanyRepository.save(historyCompany);
            }
        }

        return ptPhoneHistoryCompanyRepository.findByCompanyIdAndStartTimeBetween(companyId,
                begin, end, pageable);
    }
}
