package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtAnalyContactCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcAnalyContactCountRepository extends JpaRepository<PtAnalyContactCount, Integer> {
    /**
     * 查询某一段时间的统计聚合数据
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 集合
     */
    List<PtAnalyContactCount> findByAnalyDateBetween(Date begin, Date end);
}
