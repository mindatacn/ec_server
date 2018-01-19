package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtCompanyRepository extends JpaRepository<PtCompany, Long> {
    /**
     * 根据ec的公司id查询
     *
     * @param cropId 公司id
     * @return 公司
     */
    PtCompany findByCorpId(Long cropId);

    /**
     * 根据productId查寻
     *
     * @param productId productId
     * @return Integer
     */
    Integer countByProductId(Long productId);

    /**
     * 根据名称模糊查询
     * @param name name
     * @return List
     */
    List<PtCompany> findByNameLike(String name);
}
