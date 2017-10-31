package com.mindata.ecserver.main.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_analy_sales_day")
public class EcAnalySalesDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date saleDay;
    /**
     * 营销企业数量
     */
    private Integer saleCompNum;
    /**
     * 营销客户数量
     */
    private Integer saleCustomerNum;
    /**
     * 有效企业数量
     */
    private Integer validCompNum;
    /**
     * 有效客户数量
     */
    private Integer validCustomerNum;
    /**
     * 有意向企业数量
     */
    private Integer intentCompNum;
    /**
     * 有意向客户数量
     */
    private Integer intentCustomerNum;
    /**
     * 成交客户数量
     */
    private Integer sucCustomerNum;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sale_day")
    public Date getSaleDay() {
        return saleDay;
    }

    public void setSaleDay(Date saleDay) {
        this.saleDay = saleDay;
    }

    @Basic
    @Column(name = "sale_comp_num")
    public Integer getSaleCompNum() {
        return saleCompNum;
    }

    public void setSaleCompNum(Integer saleCompNum) {
        this.saleCompNum = saleCompNum;
    }

    @Basic
    @Column(name = "sale_customer_num")
    public Integer getSaleCustomerNum() {
        return saleCustomerNum;
    }

    public void setSaleCustomerNum(Integer saleCustomerNum) {
        this.saleCustomerNum = saleCustomerNum;
    }

    @Basic
    @Column(name = "valid_comp_num")
    public Integer getValidCompNum() {
        return validCompNum;
    }

    public void setValidCompNum(Integer validCompNum) {
        this.validCompNum = validCompNum;
    }

    @Basic
    @Column(name = "valid_customer_num")
    public Integer getValidCustomerNum() {
        return validCustomerNum;
    }

    public void setValidCustomerNum(Integer validCustomerNum) {
        this.validCustomerNum = validCustomerNum;
    }

    @Basic
    @Column(name = "intent_comp_num")
    public Integer getIntentCompNum() {
        return intentCompNum;
    }

    public void setIntentCompNum(Integer intentCompNum) {
        this.intentCompNum = intentCompNum;
    }

    @Basic
    @Column(name = "intent_customer_num")
    public Integer getIntentCustomerNum() {
        return intentCustomerNum;
    }

    public void setIntentCustomerNum(Integer intentCustomerNum) {
        this.intentCustomerNum = intentCustomerNum;
    }

    @Basic
    @Column(name = "suc_customer_num")
    public Integer getSucCustomerNum() {
        return sucCustomerNum;
    }

    public void setSucCustomerNum(Integer sucCustomerNum) {
        this.sucCustomerNum = sucCustomerNum;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (saleDay != null ? saleDay.hashCode() : 0);
        result = 31 * result + (saleCompNum != null ? saleCompNum.hashCode() : 0);
        result = 31 * result + (saleCustomerNum != null ? saleCustomerNum.hashCode() : 0);
        result = 31 * result + (validCompNum != null ? validCompNum.hashCode() : 0);
        result = 31 * result + (validCustomerNum != null ? validCustomerNum.hashCode() : 0);
        result = 31 * result + (intentCompNum != null ? intentCompNum.hashCode() : 0);
        result = 31 * result + (intentCustomerNum != null ? intentCustomerNum.hashCode() : 0);
        result = 31 * result + (sucCustomerNum != null ? sucCustomerNum.hashCode() : 0);
        return result;
    }
}
