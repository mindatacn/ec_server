package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 不同平台的产品（如EC，纷享销客）
 *
 * @author wuweifeng wrote on 2018/1/18.
 */
@Entity
@Table(name = "pt_product")
public class PtProduct extends BaseEntity {
    /**
     * 产品名字
     */
    private String name;
    /**
     * 当前状态（0正常，1内测，-1异常，异常时使用该产品的所有用户禁止操作）
     */
    private Integer state;
    /**
     * db描述，可能不同的产品将来连不同的库、表
     */
    private String db;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    @Override
    public String toString() {
        return "PtProduct{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", db='" + db + '\'' +
                '}';
    }
}
