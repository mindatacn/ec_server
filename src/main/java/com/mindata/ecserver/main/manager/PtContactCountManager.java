package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtAnalyContactCount;
import com.mindata.ecserver.main.repository.secondary.EcAnalyContactCountRepository;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Service
public class PtContactCountManager {
    /**
     * 数量聚合管理类
     */
    @Resource
    private EcAnalyContactCountRepository contactCountRepository;
    /**
     * 爬取的原始数据管理类
     */
    @Resource
    private EcContactManager ecContactManager;
    /**
     * 推送结果管理类
     */
    @Resource
    private PtPushResultManager ptPushResultManager;


    /**
     * 查询某段时间的聚合数量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 聚合
     */
    public List<PtAnalyContactCount> findByDateBetween(Date begin, Date end) {
        //查询这一段时间的聚合数量
        return contactCountRepository.findByAnalyDateBetween(begin, end);
    }

    /**
     * 添加某天的统计数据
     *
     * @return 单条数据
     */
    public PtAnalyContactCount add(Date date) {
        PtAnalyContactCount entity = new PtAnalyContactCount();
        entity.setCreateTime(new Date());
        entity.setAnalyDate(date);
        Date begin = DateUtil.beginOfDay(date);
        Date end = DateUtil.endOfDay(date);
        entity.setCompCount(ecContactManager.countByDate(begin, end));
        entity.setPushCount(ptPushResultManager.countAllByDate(begin, end));
        entity.setPushSuccessCount(ptPushResultManager.countSuccessByDate(begin, end));
        //TODO 其他的统计数据，如已沟通和有意向

        return contactCountRepository.save(entity);
    }

    public static void main(String[] args) {
        String begin = "2017-10-2";
        String end = "2017-10-5";
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date tempBegin = beginDate;
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        Date tempEnd = endDate;
        long betweenDay = DateUtil.between(beginDate, endDate, DateUnit.DAY);
        System.out.println(betweenDay);
        for (; tempBegin.before(tempEnd); tempBegin = DateUtil.offsetDay(tempBegin, 1)) {
            System.out.println(tempBegin);
        }
    }
}
