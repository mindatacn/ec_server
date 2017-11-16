package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtContactStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtContactStageRepository extends JpaRepository<PtContactStage, Long> {
    /**
     * 获取公司的状态集合
     *
     * @param companyId
     *         公司id
     * @return 集合
     */
    List<PtContactStage> findByCompanyIdOrderByStage(Long companyId);
}
