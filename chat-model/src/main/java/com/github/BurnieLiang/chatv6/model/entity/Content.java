package com.github.BurnieLiang.chatv6.model.entity;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * 用户登录成功后的上下文
 * @author Boning Liang
 * @date 2020-09-15 13:39:32
 */
@Data
public class Content {

    private Account myAccount;

    private Map<String, Friend> friends;

}
