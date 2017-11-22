package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPushFailureResult;
import com.mindata.ecserver.main.model.secondary.PtSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtSearchConditionRepository extends JpaRepository<PtSearchCondition, Long> {
    Page<PtPushFailureResult> findAll(Specification<PtSearchCondition> var1, Pageable var2);
}
