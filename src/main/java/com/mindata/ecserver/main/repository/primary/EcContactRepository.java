package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcContactRepository extends JpaRepository<EcContactEntity, Long>,
        JpaSpecificationExecutor<EcContactEntity> {
    /**
     * 获取某天的爬取数量
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 数量
     */
    int countByCreateTimeBetween(Date begin, Date end);

    /**
     * 根据id集合查询线索集合
     *
     * @param ids
     *         id集合
     * @return 线索集合
     */
    List<EcContactEntity> findByIdIn(List<Long> ids);


    EcContactEntity findById(Long id);

    List<EcContactEntity> findByState(Integer state);

    /**
     * 按省份查询数据总量
     * @return
     * 分组后的集合
     */
    @Query(value = "SELECT province, COUNT(*) FROM ec_contact_no_push WHERE state = 0 GROUP BY province", nativeQuery =
            true)
    List<Object[]> findCountByProvince();
}
