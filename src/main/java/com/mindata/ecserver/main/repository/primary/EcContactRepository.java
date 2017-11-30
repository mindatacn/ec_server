package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * 根据手机号查询
     *
     * @param mobile
     *         mobile
     * @return 记录
     */
    EcContactEntity findByMobile(String mobile);

    /**
     * 2017-11-28，给某公司导入通话信息时使用的，里面的fax字段我存放了公司员工的名字
     *
     * @param fax
     *         公司员工的名字
     * @param pageable
     *         分页
     * @return 集合
     */
    Page<EcContactEntity> findByFax(String fax, Pageable pageable);

    /**
     * 按省份查询数据总量
     * @return
     * 分组后的集合
     */
    @Query(value = "SELECT province, COUNT(province) FROM EcContactEntity WHERE state = 0 GROUP BY province")
    List<Object[]> findCountByProvince();

    /**
     * 按城市查询数据总量
     *
     * @return 分组后的集合
     */
    @Query(value = "SELECT city, COUNT(city) FROM EcContactEntity WHERE state = 0 AND province = ?1 GROUP BY city")
    List<Object[]> findCountByCity(Integer province);

    /**
     * 按行业查询数据总量
     *
     * @return 分组后的集合
     */
    @Query(value = "SELECT vocation, COUNT(vocation) FROM EcContactEntity WHERE state = 0 AND city = ?1 GROUP BY " +
            "vocation")
    List<Object[]> findCountByVocation(Integer city);
}
