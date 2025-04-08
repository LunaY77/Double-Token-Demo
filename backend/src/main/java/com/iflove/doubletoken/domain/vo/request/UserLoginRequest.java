package com.iflove.doubletoken.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户登录请求")
public class UserLoginRequest {

    @NotNull
    @Schema(description = "登录的用户名")
    private String username;

    @NotNull
    @Schema(description = "登录的用户密码")
    private String password;
}
