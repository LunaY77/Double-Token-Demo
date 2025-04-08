package com.iflove.doubletoken.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.doubletoken.common.constant.Const;
import com.iflove.doubletoken.common.constant.RedisKey;
import com.iflove.doubletoken.common.domain.dto.RequestInfo;
import com.iflove.doubletoken.common.exception.BusinessException;
import com.iflove.doubletoken.common.exception.CommonErrorEnum;
import com.iflove.doubletoken.common.exception.UnauthorizedException;
import com.iflove.doubletoken.common.utils.JWTUtil;
import com.iflove.doubletoken.common.utils.RedisUtils;
import com.iflove.doubletoken.domain.entity.User;
import com.iflove.doubletoken.domain.vo.request.UserLoginRequest;
import com.iflove.doubletoken.domain.vo.request.UserRegisterRequest;
import com.iflove.doubletoken.domain.vo.response.LoginInfoResponse;
import com.iflove.doubletoken.mapper.UserMapper;
import com.iflove.doubletoken.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author cangjingyue
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2025-04-07 20:53:18
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void register(UserRegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (lambdaQuery().eq(User::getName, registerRequest.getUsername()).count() > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建用户实体
        User user = User.builder()
                .name(registerRequest.getUsername())
                .password(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()))
                .build();

        // 保存到数据库
        if (!save(user)) {
            throw new RuntimeException("用户注册失败");
        }
    }

    @Override
    public LoginInfoResponse login(UserLoginRequest userLoginRequest) {
        // 1. 用户验证逻辑
        User user = lambdaQuery().eq(User::getName, userLoginRequest.getUserName()).one();
        // 1.1 用户不存在
        if (Objects.isNull(user)) {
            throw new BusinessException(CommonErrorEnum.USER_NOT_FOUND);
        }
        // 1.2 密码错误
        if (!BCrypt.checkpw(userLoginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(CommonErrorEnum.USER_PASSWORD_ERROR);
        }

        // 2. 生成双token
        RequestInfo requestInfo = RequestInfo.builder()
                .name(user.getName())
                .userId(user.getId())
                .build();
        // 2.1 accessToken
        String accessToken = JWTUtil.generateAccessToken(requestInfo);
        // 2.2 refreshToken
        String refreshTokenKey = String.valueOf(UUID.randomUUID());
        RedisUtils.set(RedisKey.getKey(RedisKey.REFRESH_TOKEN, refreshTokenKey), requestInfo, Const.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        // 3. 返回登录信息
        return LoginInfoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .accessToken(accessToken)
                .refreshToken(refreshTokenKey)
                .build();
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 1. 判断 refreshToken 是否存在
        if (Objects.isNull(refreshToken)) {
            throw new UnauthorizedException(CommonErrorEnum.TOKEN_INVALID);
        }
        // 2. 从redis中获取 requestInfo
        RequestInfo requestInfo = RedisUtils.get(RedisKey.getKey(RedisKey.REFRESH_TOKEN, refreshToken), RequestInfo.class);
        // 3. 判断 requestInfo 是否存在
        if (Objects.isNull(requestInfo)) {
            throw new UnauthorizedException(CommonErrorEnum.TOKEN_INVALID);
        }
        // 4. 生成新的 accessToken
        String accessToken = JWTUtil.generateAccessToken(requestInfo);
        // 5. 返回新的 accessToken
        return accessToken;
    }

    @Override
    public void logout(String refreshToken) {
        if (Objects.isNull(refreshToken)) {
            return;
        }
        // 删除 refreshToken
        try {
            RedisUtils.del(RedisKey.getKey(RedisKey.REFRESH_TOKEN, refreshToken));
        } catch (Exception ignored) {
        }
    }
}




