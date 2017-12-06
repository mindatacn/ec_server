package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class UserHistoryStateVO {
    private String name;
    private String hasDone;
    private String noDone;
    private String shouldDone;
    /**
     * 比例
     */
    private String scale;
    private String noScale;

    public String getNoScale() {
        return noScale;
    }

    public void setNoScale(String noScale) {
        this.noScale = noScale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHasDone() {
        return hasDone;
    }

    public void setHasDone(String hasDone) {
        this.hasDone = hasDone;
    }

    public String getNoDone() {
        return noDone;
    }

    public void setNoDone(String noDone) {
        this.noDone = noDone;
    }

    public String getShouldDone() {
        return shouldDone;
    }

    public void setShouldDone(String shouldDone) {
        this.shouldDone = shouldDone;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "UserHistoryStateVO{" +
                "name='" + name + '\'' +
                ", hasDone='" + hasDone + '\'' +
                ", noDone='" + noDone + '\'' +
                ", shouldDone='" + shouldDone + '\'' +
                ", scale='" + scale + '\'' +
                ", noScale='" + noScale + '\'' +
                '}';
    }
}
