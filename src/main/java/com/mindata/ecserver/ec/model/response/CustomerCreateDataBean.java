package com.mindata.ecserver.ec.model.response;

import java.util.List;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public class CustomerCreateDataBean {
    private List<CreateFailureRecord> failureRecordList;
    private Map<String, Long> successCrmIds;

    public List<CreateFailureRecord> getFailureRecordList() {
        return failureRecordList;
    }

    public void setFailureRecordList(List<CreateFailureRecord> failureRecordList) {
        this.failureRecordList = failureRecordList;
    }

    public Map<String, Long> getSuccessCrmIds() {
        return successCrmIds;
    }

    public void setSuccessCrmIds(Map<String, Long> successCrmIds) {
        this.successCrmIds = successCrmIds;
    }
}
