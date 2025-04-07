package com.iflove.doubletoken.service;

import com.iflove.doubletoken.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.doubletoken.domain.vo.request.UserLoginRequest;
import com.iflove.doubletoken.domain.vo.request.UserRegisterRequest;
import com.iflove.doubletoken.domain.vo.response.LoginInfoResponse;
import jakarta.validation.Valid;

/**
* @author cangjingyue
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-04-07 20:53:18
*/
public interface UserService extends IService<User> {

    void register(UserRegisterRequest registerRequest);

    LoginInfoResponse login(UserLoginRequest userLoginRequest);
}
