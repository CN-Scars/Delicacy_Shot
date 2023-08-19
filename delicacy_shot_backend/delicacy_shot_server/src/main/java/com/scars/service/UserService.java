package com.scars.service;

import com.scars.dto.UserLoginDTO;
import com.scars.entity.User;

import javax.security.auth.login.LoginException;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     * @throws LoginException
     */
    User wechatLogin(UserLoginDTO userLoginDTO) throws LoginException;
}
