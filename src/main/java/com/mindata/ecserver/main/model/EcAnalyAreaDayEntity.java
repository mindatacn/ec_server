package com.mindata.ecserver.main.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_analy_area_day")
public class EcAnalyAreaDayEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date pushDay;
    private Integer province;
    private Integer num;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "push_day")
    public Date getPushDay() {
        return pushDay;
    }

    public void setPushDay(Date pushDay) {
        this.pushDay = pushDay;
    }

    @Basic
    @Column(name = "province")
    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    @Basic
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pushDay != null ? pushDay.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        return result;
    }
}
