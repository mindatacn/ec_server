package com.mindata.ecserver.main.vo;

/**
 * @author hanliqiang wrote on 2018/1/19
 */
public class CompanyThresholdVO {
    private Long id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 默认推送数量阈值
     */
    private Integer threshold;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "CompanyVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", threshold=" + threshold +
                '}';
    }
}
