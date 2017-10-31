package com.mindata.ecserver.main.repository;

import com.mindata.ecserver.main.model.EcAnalyContactCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcAnalyContactCountRepository extends JpaRepository<EcAnalyContactCountEntity, Integer> {
    /**
     * 查询某一段时间的统计聚合数据
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 集合
     */
    List<EcAnalyContactCountEntity> findByAnalyDateBetween(Date begin, Date end);
}
