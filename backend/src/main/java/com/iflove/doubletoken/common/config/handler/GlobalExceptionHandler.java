package com.iflove.doubletoken.common.config.handler;

import com.iflove.doubletoken.common.domain.vo.response.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 全局异常捕获器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 未知异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public RestBean<Void> systemExceptionHandler(Exception e) {
        log.error("system exception！The reason is：{}", e.getMessage(), e);
        return RestBean.failure(500, "系统异常，请联系管理员！");
    }
}
