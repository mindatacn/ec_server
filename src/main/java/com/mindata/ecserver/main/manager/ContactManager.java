package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Service
public class ContactManager {
    @Autowired
    private EcContactRepository contactRepository;

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

}
