package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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

    /**
     * 复杂条件查询
     * @param pushed
     * 是否已推送
     * @param var1
     * 复杂条件
     * @param var2
     * 分页
     * @return
     * 结果
     */
    //Page<EcContactEntity> findByPushed(boolean pushed, Specification var1, Pageable var2);

    EcContactEntity findByIdAndState(Long id, Integer state);


    List<EcContactEntity> findByStateAndCompanyLike(Integer state,String company);

    List<EcContactEntity> findByState(Integer state);
}
