package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPushSuccessResultRepository extends JpaRepository<PtPushSuccessResult, Integer>,
        JpaSpecificationExecutor<PtPushSuccessResult> {
    /**
     * 统计已推送的数量
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 数量
     */
    int countByCreateTimeBetween(Date begin, Date end);

    /**
     * 根据crmId查询结果
     *
     * @param crmId
     *         crmId
     * @return 结果
     */
    PtPushSuccessResult findByCrmId(Integer crmId);
}
