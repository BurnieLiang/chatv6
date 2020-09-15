package com.github.BurnieLiang.chatv6.service;

import com.github.BurnieLiang.chatv6.model.entity.Account;

/**
 * @author Boning Liang
 * @date 2020-09-15 13:33:49
 */
public interface AuthService {

    /**
     * 注册用户
     * @param username 用户名，用来登录
     * @param accountName 账户名，对外的用户名，用来添加好友
     * @param nickname  显示的昵称
     * @param password 登录密码，传明文，创建好账户后以密文形式保存，不可逆，密码不可找回，请慎重
     * @return Account 创建的账户
     */
    Account register(String username, String accountName, String nickname, String password);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的账户
     */
    Account login(String username, String password);

}
