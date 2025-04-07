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
public class UserLoginRequest {

    @NotNull
    @Schema(description = "登录的用户ID")
    private String userId;

    @NotNull
    @Schema(description = "登录的用户密码")
    private String password;
}
