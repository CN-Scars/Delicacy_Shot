package com.scars.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scars.constant.MessageConstant;
import com.scars.dto.UserLoginDTO;
import com.scars.entity.User;
import com.scars.mapper.UserMapper;
import com.scars.properties.WeChatProperties;
import com.scars.service.UserService;
import com.scars.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    // 微信服务接口地址
    public static final String WECHAT_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     * @throws LoginException
     */
    public User wechatLogin(UserLoginDTO userLoginDTO) throws LoginException {
        String openid = getOpenid(userLoginDTO.getCode());

        // openid为空代表登录失败
        if (openid == null) {
            throw new LoginException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenid(openid);

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    /**
     * 调用微信接口服务获取当前微信用户的Openid
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WECHAT_LOGIN, map);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");

        return openid;
    }
}
