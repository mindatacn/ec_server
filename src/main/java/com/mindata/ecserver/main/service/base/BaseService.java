package com.mindata.ecserver.main.service.base;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
@Service
public class BaseService {

    @Resource
    protected ApplicationEventPublisher eventPublisher;
}
