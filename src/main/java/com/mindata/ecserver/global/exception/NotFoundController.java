package com.mindata.ecserver.global.exception;

import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.bean.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuweifeng wrote on 2017/10/23.
 * 404处理
 */
@RestController
public class NotFoundController implements ErrorController {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public Object error() {
        logger.error("---------------404了，请求的url不存在---------------");
        // 错误处理逻辑
        return ResultGenerator.genFailResult(ResultCode.NOT_FOUND, "页面不存在");
    }
}
