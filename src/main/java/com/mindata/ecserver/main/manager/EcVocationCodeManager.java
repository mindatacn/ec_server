package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import com.mindata.ecserver.main.repository.primary.EcVocationCodeEntityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
@Service
public class EcVocationCodeManager {
    @Resource
    private EcVocationCodeEntityRepository ecVocationCodeEntityRepository;

    /**
     * 根据code查询类型名称
     *
     * @param vocationCode
     *         主键id
     * @return
     */
    public String findNameByCode(int vocationCode) {
        return ecVocationCodeEntityRepository.findOne(vocationCode).getVocationName();
    }

    /**
     * 查询所有code等于vocationCode，或者parentCode等于vocationCode的
     */
    public List<EcVocationCodeEntity> findAllRelated(Integer vocationCode) {
        return ecVocationCodeEntityRepository.findByVocationCodeOrParentCode(vocationCode, vocationCode);
    }

    public List<EcVocationCodeEntity> findAll() {
        return ecVocationCodeEntityRepository.findAll();
    }
}
