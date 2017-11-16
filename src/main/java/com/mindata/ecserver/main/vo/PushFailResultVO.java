package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author hanliqiang wrote on 2017/11/16
 */
public class PushFailResultVO {
    /**
     * 编号Id
     */
    private Long id;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 推送时间
     */
    private Date pushTime;
    /**
     * 失败原因
     */
    private String failReasion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getFailReasion() {
        return failReasion;
    }

    public void setFailReasion(String failReasion) {
        this.failReasion = failReasion;
    }
}
