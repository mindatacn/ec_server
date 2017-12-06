package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 推送结果数量VO
 */
public class PushResultCountVO {
    private Integer successCount;
    private Integer failureCount;

    public PushResultCountVO(Integer successCount, Integer failureCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    @Override
    public String toString() {
        return "PushResultCountVO{" +
                "successCount=" + successCount +
                ", failureCount=" + failureCount +
                '}';
    }
}
