package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import com.xiaoleilu.hutool.io.file.FileReader;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Resource
    private EcContactRepository contactRepository;
    @Resource
    private EcContactRepository ecContactRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

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
     * 查询每个省份下按城市分组的数量
     *
     * @return list
     */
    public List<Object[]> findCountByCity(Integer province) {
        return contactRepository.findCountByCity(province);
    }

    /**
     * 查询每个城市下按行业分组的数量
     *
     * @return list
     */
    public List<Object[]> findCountByVocation(Integer city) {
        return contactRepository.findCountByVocation(city);
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

    /**
     * 从Excel表读取数据插入到Contact表
     */
    public void excelToContact(String filePath) {
        logger.info("开始读取Excel表格，准备将客户数据插入到Contact表");
        FileReader fileReader = new FileReader(filePath);
        List<String> list = fileReader.readLines();
        list.forEach(this::add);
        logger.info("客户数据插入到Contact表完毕，共" + list.size() + "条数据");
    }

    /**
     * 2017-11-28日，某客户信息导入到Contact表，从Excel读取
     *
     * @param rowStr
     *         一行数据
     */
    private void add(String rowStr) {
        String[] array = rowStr.split(",");
        String mobile = array[3];
        EcContactEntity ecContactEntity = ecContactRepository.findByMobile(mobile);
        if (ecContactEntity == null) {
            ecContactEntity = new EcContactEntity();
            ecContactEntity.setState(0);
            ecContactEntity.setName(array[0]);
            //设置为操作员名字，先用fax字段存着
            ecContactEntity.setFax(array[1]);
            if (StrUtil.isEmpty(array[2])) {
                ecContactEntity.setGender(0);
            } else if ("男".equals(array[2])) {
                ecContactEntity.setGender(1);
            } else {
                ecContactEntity.setGender(2);
            }
            ecContactEntity.setMobile(array[3]);
            ecContactEntity.setEmail(array[4]);
            ecContactRepository.save(ecContactEntity);
        }
    }

}
