package com.iflove.doubletoken.controller;

;
import com.iflove.doubletoken.domain.vo.request.UserLoginRequest;
import com.iflove.doubletoken.domain.vo.request.UserRegisterRequest;
import com.iflove.doubletoken.common.domain.vo.response.RestBean;
import com.iflove.doubletoken.domain.vo.response.LoginInfoResponse;
import com.iflove.doubletoken.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("/capi/user/public")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户公共接口", description = "用户公共接口")
public class AuthorizeController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public RestBean<LoginInfoResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return RestBean.success(userService.login(userLoginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public RestBean<Void> register(@RequestBody @Valid UserRegisterRequest registerRequest) {
        userService.register(registerRequest);
        return RestBean.success();
    }

}