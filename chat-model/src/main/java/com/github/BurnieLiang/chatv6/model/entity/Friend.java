package com.github.BurnieLiang.chatv6.model.entity;

import lombok.Data;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Boning Liang
 * @date 2020-09-15 11:19:19
 */
@Data
public class Friend {

    /**
     * 朋友的公钥，用于给朋友发送私信
     */
    private PublicKey publicKey;

    /**
     * 朋友的ip地址
     */
    private List<String> addresses;

    /**
     * 上一次通信时朋友的客户端版本
     */
    private String version;

    /**
     * 添加时间
     */
    private LocalDateTime addDateTime;

    /**
     *  账号名
     */
    private String friendsUsername;

    /**
     * 朋友昵称
     */
    private String nickname;

    /**
     * 备注名
     */
    private String aliasName;

    /**
     * 上一条发送/接收消息的时间
     */
    private LocalDateTime lastMsgDateTime;

}
