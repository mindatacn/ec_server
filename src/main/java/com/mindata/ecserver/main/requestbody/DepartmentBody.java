package com.mindata.ecserver.main.requestbody;

/**
 * @author wuweifeng wrote on 2018/3/1.
 */
public class DepartmentBody {
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 部门leader（id1，id2）
     */
    private String leaders;
    /**
     * 在EC的sort
     */
    private Integer sort;
    /**
     * 默认推送数量阈值
     */
    private Integer threshold;
    /**
     * 备注
     */
    private String memo;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
