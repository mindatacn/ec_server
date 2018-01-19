package com.mindata.ecserver.global.constant;

import org.springframework.data.domain.Sort;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface Constant {
    String SALT = "mindata";
    /**
     * 正常态
     */
    int STATE_NORMAL = 0;

    int STATE_FAIL = 2;

    int STATE_NO_PUSH = 0;

    String ES_INDEX_NAME = "ec-server";
    String ES_TYPE_NAME = "contact";
    String ES_GEO_INDEX_NAME = "geo";
    String ES_COORDINATE_TYPE_NAME = "coordinate";

    int SUCCESS = 1;
    int FAILURE = 0;

    String ROLE_ADMIN = "admin";
    //String ROLE_MANAGER = "manager";
    //String ROLE_LEADER = "leader";
    //String ROLE_USER = "user";

    Long MANAGER_COMPANY_ID = 0L;

    String CONTENT_TYPE = "Content-Type";
    String APP_JSON = "application/json";

    /**
     * 用户访问任何接口header里带的token
     */
    String AUTHORIZATION = "token";


    int PAGE_SIZE = 10;
    int PAGE_NUM = 0;
    Sort.Direction DIRECTION = Sort.Direction.DESC;
}
