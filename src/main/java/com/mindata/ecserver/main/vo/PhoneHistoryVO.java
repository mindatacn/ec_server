package com.mindata.ecserver.main.vo;

import com.mindata.ecserver.global.bean.SimplePage;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryVO {
    private PhoneHistoryBeanVO total;
    private SimplePage<PhoneHistoryBeanVO> page;

    public PhoneHistoryBeanVO getTotal() {
        return total;
    }

    public void setTotal(PhoneHistoryBeanVO total) {
        this.total = total;
    }

    public SimplePage<PhoneHistoryBeanVO> getPage() {
        return page;
    }

    public void setPage(SimplePage<PhoneHistoryBeanVO> page) {
        this.page = page;
    }
}
