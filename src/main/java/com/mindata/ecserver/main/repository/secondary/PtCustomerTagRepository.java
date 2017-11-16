package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCustomerTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public interface PtCustomerTagRepository extends JpaRepository<PtCustomerTag, Long> {
    /**
     * 根据标签Id查找标签
     * @param classId 标签Id
     * @return
     * tag
     */
    PtCustomerTag findByClassId(Long classId);
}
