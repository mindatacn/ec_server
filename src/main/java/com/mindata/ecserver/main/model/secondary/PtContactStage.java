package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 客户跟进状态表（各公司可以自定义，如已沟通，已成单，待沟通等）
 */
@Entity
@Table(name = "pt_contact_stage")
public class PtContactStage extends BaseEntity {

    /**
     * 阶段名称
     */
    private String name;
    /**
     * 状态的深度，以最大的为准。譬如某客户有多个状态（待沟通、在跟进），那么就以"在跟进"为该用户的状态
     */
    private Integer stage;
    /**
     * 公司id
     */
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
