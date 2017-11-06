package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class UserAccountData extends BaseEcData {
    private UserAccountDataBean data;

    public UserAccountDataBean getData() {
        return data;
    }

    public void setData(UserAccountDataBean data) {
        this.data = data;
    }
}
