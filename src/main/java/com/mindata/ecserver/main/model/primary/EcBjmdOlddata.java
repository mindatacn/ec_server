package com.mindata.ecserver.main.model.primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/12/11.
 */
@Entity
@Table(name = "ec_bjmd_olddata")
public class EcBjmdOlddata {
    @Id
    private Long crmId;

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }
}
