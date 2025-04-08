package com.iflove.doubletoken.common.utils;

import com.iflove.doubletoken.common.constant.Const;
import com.iflove.doubletoken.common.domain.dto.RequestInfo;
import com.iflove.doubletoken.common.exception.CommonErrorEnum;
import com.iflove.doubletoken.common.exception.UnauthorizedException;
import com.iflove.doubletoken.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;


/**
 * @author 苍镜月
 * @version 1.0
 * @implNote JWT工具类
 */

@Slf4j
public class JWTUtil {

    // token前缀
    public static final String BEARER = "Bearer ";

    /**
     * 生成用户 Token
     *
     * @param user 用户信息
     * @return 用户访问 Token
     */
    public static String generateAccessToken(RequestInfo user) {
        Map<String, Object> userInfoMap = Map.of(Const.USER_ID_KEY, user.getUserId(), Const.USER_NAME_KEY, user.getName());
        String jwtToken = Jwts.builder()
                .addClaims(userInfoMap)
                .setIssuedAt(new Date())
                .setIssuer(Const.ISS)
                .setExpiration(new Date(System.currentTimeMillis() + Const.ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(Const.KEY, SignatureAlgorithm.HS256)
                .compact();
        return BEARER + jwtToken;
    }

    /**
     * 解析用户 Token
     *
     * @param jwtToken 用户访问 Token
     * @return 用户信息
     */
    public static RequestInfo parseJwtToken(String jwtToken) {
        if (StringUtils.hasText(jwtToken)) {
            String actualJwtToken = jwtToken.replace(BEARER, "");
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Const.KEY)
                        .build()
                        .parseClaimsJws(actualJwtToken)
                        .getBody();
                
                return RequestInfo.builder()
                        .userId(claims.get(Const.USER_ID_KEY, Long.class))
                        .name(claims.get(Const.USER_NAME_KEY, String.class))
                        .build();
            } catch (Exception ex) {
                log.warn("JWT Token解析失败，请检查", ex);
                throw new UnauthorizedException(CommonErrorEnum.TOKEN_INVALID);
            }
        }
        return null;
    }

}
