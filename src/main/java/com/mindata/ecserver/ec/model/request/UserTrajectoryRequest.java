package com.mindata.ecserver.ec.model.request;

import java.util.List;

/**
 * 跟进记录
 *
 * @author wuweifeng wrote on 2017/11/28.
 */
public class UserTrajectoryRequest {
    private List<UserTrajector> list;

    public List<UserTrajector> getList() {
        return list;
    }

    public void setList(List<UserTrajector> list) {
        this.list = list;
    }
}
