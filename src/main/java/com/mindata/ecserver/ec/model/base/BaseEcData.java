package com.mindata.ecserver.ec.model.base;

/**
 * @author wuweifeng
 * EC返回值
 */
public class BaseEcData {
    private int errCode;
    private String errMsg;

    public BaseEcData() {
    }

    public BaseEcData(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "BaseEcData{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
