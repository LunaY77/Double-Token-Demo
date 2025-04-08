package com.iflove.doubletoken.common.exception;

import lombok.AllArgsConstructor;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 服务错误枚举
 */
@AllArgsConstructor
public enum CommonErrorEnum implements ErrorEnum {
    SYSTEM_ERROR(501, "服务器出错，请联系管理员"),
    PARAM_VALID(502, "参数校验失败"),
    LOCK_LIMIT(503, "请求频繁，请稍后再试"),
    TOKEN_INVALID(401, "token无效"),
    USER_NOT_FOUND(505, "用户不存在"),
    USER_PASSWORD_ERROR(506, "用户名或密码错误"),
    USER_NAME_EXITS(507, "用户名已存在"),
    REGISTER_ERROR(508, "注册失败"),
    ;
    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }
}
