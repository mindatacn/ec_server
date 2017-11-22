package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Service
public class EcContactManager {
    @Autowired
    private EcContactRepository contactRepository;
    @Resource
    private EcContactRepository ecContactRepository;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    /**
     * 获取某天的爬取数量
     *
     * @param begin
     *         开始日期
     * @param end
     *         结束日期
     * @return 某天的数量
     */
    public int countByCreateTimeBetween(Date begin, Date end) {
        return contactRepository.countByCreateTimeBetween(begin, end);
    }

    /**
     * 查询每个省份下的数量
     *
     * @return list
     */
    public List<Object[]> findCountByProvince() {
        return contactRepository.findCountByProvince();
    }

    /**
     * 根据id集合查询所有的线索
     *
     * @param ids
     *         线索id集合
     * @return 线索集合
     */
    public List<EcContactEntity> findByIds(List<Long> ids) {
        return contactRepository.findByIdIn(ids);
    }

    public EcContactEntity findOne(Long id) {
        return contactRepository.findOne(id);
    }

    public EcContactEntity update(EcContactEntity ecContactEntity) {
        return contactRepository.save(ecContactEntity);
    }

    /**
     * 根据Id和状态查找结果
     * @param id
     * id
     * @return
     * 结果
     */
    public EcContactEntity findById(Long id) {
        return contactRepository.findById(id);
    }


    public Page<EcContactEntity> findAll(Specification<EcContactEntity> criteria, Pageable pageable) {
        return ecContactRepository.findAll(criteria, pageable);
    }
}
