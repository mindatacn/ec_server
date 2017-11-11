package com.mindata.ecserver.main.requestbody;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class PushBody {
    private List<Integer> ids;
    /**
     * 跟进人id
     */
    private Long followUserId;

    private Long optUserId;

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "PushBody{" +
                "ids=" + ids +
                ", followUserId=" + followUserId +
                ", optUserId=" + optUserId +
                '}';
    }
}
