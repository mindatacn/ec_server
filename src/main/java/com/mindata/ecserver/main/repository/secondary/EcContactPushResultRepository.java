package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.EcContactPushResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcContactPushResultRepository extends JpaRepository<EcContactPushResultEntity, Integer> {
    /**
     * 统计已推送的数量
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 数量
     */
    int countByPushTimeBetween(Date begin, Date end);

    /**
     * 统计已推送的数量
     *
     * @param status
     *         是否成功的标志
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 数量
     */
    int countByStatusAndPushTimeBetween(int status, Date begin, Date end);
}
