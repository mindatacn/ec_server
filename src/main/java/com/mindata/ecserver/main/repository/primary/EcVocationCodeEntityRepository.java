package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
public interface EcVocationCodeEntityRepository extends JpaRepository<EcVocationCodeEntity, Integer> {
    /**
     * 根据code或parentCode来查询
     *
     * @param code
     *         code
     * @param parentCode
     *         parentCode
     * @return 集合
     */
    List<EcVocationCodeEntity> findByVocationCodeOrParentCode(Integer code, Integer parentCode);
}
