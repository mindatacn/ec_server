package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
public class PhoneHistory extends BaseEcData {
    private PhoneHistoryData data;

    public PhoneHistoryData getData() {
        return data;
    }

    public void setData(PhoneHistoryData data) {
        this.data = data;
    }
}
