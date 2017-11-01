package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.manager.PtContactCountManager;
import com.mindata.ecserver.main.model.secondary.PtAnalyContactCount;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Service
public class AnalyContactCountService {
    @Resource
    private PtContactCountManager ptContactCountManager;

    /**
     * 查询某个时间段统计数据
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果集
     */
    @Transactional(rollbackFor = Exception.class)
    public List<PtAnalyContactCount> findByDateBetween(String begin, String end) {
        Date tempBegin = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date tempEnd = DateUtil.endOfDay(DateUtil.parseDate(end));

        //查看间隔多少天
        long betweenDay = DateUtil.between(tempBegin, tempEnd, DateUnit.DAY);
        //获取表中该时间段的统计数据集合
        List<PtAnalyContactCount> contactCountEntityList = ptContactCountManager.findByDateBetween(tempBegin,
                tempEnd);
        //间隔数量应该=数据库数量-1，譬如开始和结束都是今天，数据会有一条数据
        if (contactCountEntityList.size() == betweenDay + 1) {
            //返回数据库数据
            return contactCountEntityList;
        }

        //清空集合，存放下面的数据
        contactCountEntityList.clear();
        //如果有缺失，获取begin到end间的所有天，每一天去count表查一次，把缺失的一天数据给补上
        for (; tempBegin.before(tempEnd); tempBegin = DateUtil.offsetDay(tempBegin, 1)) {
            //每一天的
            List<PtAnalyContactCount> tempList = ptContactCountManager.findByDateBetween(tempBegin,
                    tempEnd);
            if (tempList.size() > 0) {
                contactCountEntityList.addAll(tempList);
                continue;
            }
            //该天没有值，就把该天的统计数据加上
            contactCountEntityList.add(ptContactCountManager.add(tempBegin));
        }

        return contactCountEntityList;
    }
}
