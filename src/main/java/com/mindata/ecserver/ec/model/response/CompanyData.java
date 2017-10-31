package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class CompanyData extends BaseEcData {
    private CompanyDataBean data;

    public CompanyDataBean getData() {
        return data;
    }

    public void setData(CompanyDataBean data) {
        this.data = data;
    }
}
