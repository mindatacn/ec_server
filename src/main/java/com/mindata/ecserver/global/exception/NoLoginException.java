package com.mindata.ecserver.global.exception;

import org.apache.shiro.ShiroException;

/**
 * @author wuweifeng wrote on 2017/10/27.
 */
public class NoLoginException extends ShiroException {

    public NoLoginException() {
        super();
    }

    public NoLoginException(String message) {
        super(message);
    }
}
