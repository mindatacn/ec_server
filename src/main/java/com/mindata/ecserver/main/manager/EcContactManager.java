package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.mindata.ecserver.global.constant.Constant.STATE_NORMAL;
import static com.mindata.ecserver.global.constant.Constant.STATE_NO_PUSH;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Service
public class EcContactManager {
    @Autowired
    private EcContactRepository contactRepository;

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
    public int countByDate(Date begin, Date end) {
        return contactRepository.countByCreateTimeBetween(begin, end);
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
     * @return
     */
    public EcContactEntity findById(Long id) {
        return contactRepository.findById(id);
    }

    /**
     *根据状态和名称模糊查询结果
     * @param state
     * @param company
     * @return
     */
    public List<EcContactEntity> findByStateAndCompanyLike(Integer state,String company) {
        if(StrUtil.isEmpty(company)){
            return contactRepository.findByState(state);
        }
        return contactRepository.findByStateAndCompanyLike(state,"%" + company + "%");
    }


}
