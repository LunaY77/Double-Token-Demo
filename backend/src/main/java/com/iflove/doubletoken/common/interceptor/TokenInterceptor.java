package com.iflove.doubletoken.common.interceptor;


import com.iflove.doubletoken.common.domain.dto.RequestInfo;
import com.iflove.doubletoken.common.exception.CommonErrorEnum;
import com.iflove.doubletoken.common.exception.UnauthorizedException;
import com.iflove.doubletoken.common.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Slf4j
@Component
@Order(0)
public class TokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_SCHEMA = "Bearer ";
    public static final String ATTRIBUTE_USER_INFO = "userInfo";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        // 判断是否是公共方法
        if (isPublicURI(request.getRequestURI())) {
            return true;
        }

        // 从请求头获取 token： Authorization: Bearer <token>
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        // 如果为空或不是 Bearer 令牌
        if (Objects.isNull(accessToken) || !accessToken.startsWith(AUTHORIZATION_SCHEMA)) {
            log.error("token is null or not start with 'Bearer'");
            throw new UnauthorizedException(CommonErrorEnum.ACCESS_TOKEN_INVALID);
        }
        log.debug("accessToken:{}", accessToken);
        RequestInfo requestInfo = JWTUtil.parseJwtToken(accessToken);
        // 解析失败
        if (Objects.isNull(requestInfo)) {
            log.error("token is invalid");
            throw new UnauthorizedException(CommonErrorEnum.ACCESS_TOKEN_INVALID);
        }
        log.debug("userId:{}, username:{}", requestInfo.getUserId(), requestInfo.getName());
        // 将用户信息存入 request 中
        request.setAttribute(ATTRIBUTE_USER_INFO, requestInfo);

        // 继续处理请求
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    /**
     * 判断是不是公共方法，可以未登录访问的
     *
     * @param requestURI
     */
    private boolean isPublicURI(String requestURI) {
        String[] split = requestURI.split("/");
        return split.length > 2 && "public".equals(split[3]);
    }
}