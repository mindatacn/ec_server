package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
public interface EcVocationCodeEntityRepository extends JpaRepository<EcVocationCodeEntity, Integer> {
    List<EcVocationCodeEntity> findByVocationCodeOrParentCode(Integer code, Integer parentCode);
}
