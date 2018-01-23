package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtCompanyRepository extends JpaRepository<PtCompany, Long>,
        JpaSpecificationExecutor<PtCompany> {
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

    /**
     * 查询除了过期的数据
     * @param buyStatus buyStatus
     * @return List
     */
    List<PtCompany> findByBuyStatusNot(Integer buyStatus);

}
