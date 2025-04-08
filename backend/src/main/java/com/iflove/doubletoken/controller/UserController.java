package com.iflove.doubletoken.controller;

import com.iflove.doubletoken.common.constant.Const;
import com.iflove.doubletoken.common.domain.vo.response.RestBean;
import com.iflove.doubletoken.common.utils.RequestHolder;
import com.iflove.doubletoken.domain.entity.User;
import com.iflove.doubletoken.domain.vo.response.UserInfoResponse;
import com.iflove.doubletoken.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@RestController
@RequestMapping("/capi/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "UserController", description = "用户接口")
public class UserController {
    private final UserService userService;

    @GetMapping("info/me")
    @Operation(summary = "获取当前用户信息", description = "获取当前用户信息")
    public RestBean<UserInfoResponse> getUserInfo() {
        return RestBean.success(UserInfoResponse.builder()
                .id(RequestHolder.get().getUserId())
                .name(RequestHolder.get().getName())
                .build()
        );
    }

    @GetMapping("logout")
    @Operation(summary = "退出登录", description = "退出登录")
    public RestBean<Void> logout(@CookieValue(value = Const.REFRESH_TOKEN_COOKIE_NAME) String refreshToken) {
        userService.logout(refreshToken);
        return RestBean.success();
    }
}
