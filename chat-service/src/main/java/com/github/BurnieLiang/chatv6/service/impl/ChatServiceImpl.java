package com.github.BurnieLiang.chatv6.service.impl;

import com.github.BurnieLiang.chatv6.model.entity.Message;
import com.github.BurnieLiang.chatv6.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Boning Liang
 * @date 2020-09-14 09:52:55
 */
@Service("chatService")
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String send(Message msg) {
//        restTemplate.postForEntity()
        return null;
    }
}
