package com.github.BurnieLiang.chatv6.service.impl;

import com.github.BurnieLiang.chatv6.model.entity.Account;
import com.github.BurnieLiang.chatv6.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * @author Boning Liang
 * @date 2020-09-15 13:34:01
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Override
    public Account register(String username, String accountName, String nickname, String password) {
        return null;
    }

    @Override
    public Account login(String username, String password) {
        return null;
    }

}
