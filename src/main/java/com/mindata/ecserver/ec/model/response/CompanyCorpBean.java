package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class CompanyCorpBean {
    private Integer corpId;
    private String corpShortName;

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    public String getCorpShortName() {
        return corpShortName;
    }

    public void setCorpShortName(String corpShortName) {
        this.corpShortName = corpShortName;
    }

    @Override
    public String toString() {
        return "CompanyCorpBean{" +
                "corpId=" + corpId +
                ", corpShortName='" + corpShortName + '\'' +
                '}';
    }
}
