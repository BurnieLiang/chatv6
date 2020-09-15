package com.github.BurnieLiang.chatv6.service;

import com.github.BurnieLiang.chatv6.model.entity.Message;

/**
 * @author Boning Liang
 * @date 2020-09-14 09:49:19
 */
public interface ChatService {

    String send(Message msg);

}
