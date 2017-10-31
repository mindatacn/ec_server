package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

/**
 * @author wuweifeng
 * 请求token的返回值
 */
public class AccessData extends BaseEcData {
    private AccessDataBean data;

    public AccessDataBean getData() {
        return data;
    }

    public void setData(AccessDataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccessData{" +
                "data=" + data +
                '}';
    }
}
