package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public class CreateFailureRecord {
    private int index;
    private String failureCause;
    private String failureFields;
    private String existedCustomerName;
    private String existedFollowUserName;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    public String getFailureFields() {
        return failureFields;
    }

    public void setFailureFields(String failureFields) {
        this.failureFields = failureFields;
    }

    public String getExistedCustomerName() {
        return existedCustomerName;
    }

    public void setExistedCustomerName(String existedCustomerName) {
        this.existedCustomerName = existedCustomerName;
    }

    public String getExistedFollowUserName() {
        return existedFollowUserName;
    }

    public void setExistedFollowUserName(String existedFollowUserName) {
        this.existedFollowUserName = existedFollowUserName;
    }

    @Override
    public String toString() {
        return "CreateFailureRecord{" +
                "index=" + index +
                ", failureCause='" + failureCause + '\'' +
                ", failureFields='" + failureFields + '\'' +
                ", existedCustomerName='" + existedCustomerName + '\'' +
                ", existedFollowUserName='" + existedFollowUserName + '\'' +
                '}';
    }
}
