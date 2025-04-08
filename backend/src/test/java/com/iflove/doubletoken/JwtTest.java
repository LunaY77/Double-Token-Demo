package com.iflove.doubletoken;

import com.iflove.doubletoken.common.domain.dto.RequestInfo;
import com.iflove.doubletoken.common.utils.JWTUtil;
import com.iflove.doubletoken.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */


public class JwtTest {

    @Test
    public void testjwt() {
        RequestInfo requestInfo = RequestInfo.builder().name("test").userId(1L).build();
        String token = JWTUtil.generateAccessToken(requestInfo);
        System.out.println(token);
        RequestInfo user1 = JWTUtil.parseJwtToken(token);
        System.out.println(user1);
    }
}
