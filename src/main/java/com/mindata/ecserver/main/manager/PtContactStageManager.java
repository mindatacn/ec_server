package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtContactStage;
import com.mindata.ecserver.main.repository.secondary.PtContactStageRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户沟通状态管理类
 *
 * @author wuweifeng wrote on 2017/11/16.
 */
public class PtContactStageManager {
    @Resource
    private PtContactStageRepository ptContactStageRepository;

    /**
     * 查询某公司的客户状态表
     *
     * @param companyId
     *         公司id
     * @return 状态表
     */
    public List<PtContactStage> findByCompanyId(Long companyId) {
        return ptContactStageRepository.findByCompanyIdOrderByStage(companyId);
    }


}
