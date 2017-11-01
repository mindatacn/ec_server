package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 推送结果数量VO
 */
public class PushResultCountVO {
    private int successCount;
    private int failureCount;

    public PushResultCountVO(int successCount, int failureCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
}
