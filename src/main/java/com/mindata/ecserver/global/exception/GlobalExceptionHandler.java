package com.mindata.ecserver.global.exception;

import com.mindata.ecserver.ec.exception.EcException;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.bean.ResultGenerator;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.mindata.ecserver.global.bean.ResultCode.PARAMETER_ERROR;
import static org.springframework.http.HttpStatus.NOT_EXTENDED;

/**
 * @author wuweifeng wrote on 2017/10/23.
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ResultGenerator.genFailResult(PARAMETER_ERROR, "GET、POST方法错误或参数有错误"),
                NOT_EXTENDED);
    }

    /**
     * shiro没有权限操作异常
     *
     * @param e
     *         e
     * @return 结果
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public BaseData unauthorHandler(Exception e) {
        log(e);
        return ResultGenerator.genFailResult(ResultCode.NO_PERMISSION, "您没有权限操作该菜单");
    }

    @ExceptionHandler(value = NoLoginException.class)
    @ResponseBody
    public BaseData noLoginHandler(NoLoginException e) {
        log(e);
        return ResultGenerator.genFailResult(ResultCode.NO_LOGIN, "您没有登录");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public BaseData loginFailHandler(Exception e) {
        log(e);
        return ResultGenerator.genFailResult(ResultCode.LOGIN_FAIL_ERROR, "登录失败");
    }

    @ExceptionHandler(value = EcException.class)
    @ResponseBody
    public BaseData ecFail(EcException e) {
        log(e);
        return ResultGenerator.genFailResult(ResultCode.EC_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseData jsonHandler(HttpServletRequest request, Exception e) throws Exception {
        log(e);
        return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private void log(Exception ex) {
        logger.error("************************异常开始*******************************");
        logger.error(ex.toString());

        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            logger.error(stackTraceElement.toString());
        }
        logger.error("************************异常结束*******************************");
    }
}
