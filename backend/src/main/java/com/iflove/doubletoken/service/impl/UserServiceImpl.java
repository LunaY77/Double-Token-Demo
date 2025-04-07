package com.iflove.doubletoken.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.doubletoken.domain.entity.User;
import com.iflove.doubletoken.domain.vo.request.UserLoginRequest;
import com.iflove.doubletoken.domain.vo.request.UserRegisterRequest;
import com.iflove.doubletoken.domain.vo.response.LoginInfoResponse;
import com.iflove.doubletoken.service.UserService;
import com.iflove.doubletoken.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author cangjingyue
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-04-07 20:53:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    public void register(UserRegisterRequest registerRequest) {

    }

    @Override
    public LoginInfoResponse login(UserLoginRequest userLoginRequest) {
        return null;
    }
}




