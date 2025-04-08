package com.iflove.doubletoken.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Data
@Schema(description = "用户信息")
@Builder
public class UserInfoResponse {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户名")
    private String name;

}
