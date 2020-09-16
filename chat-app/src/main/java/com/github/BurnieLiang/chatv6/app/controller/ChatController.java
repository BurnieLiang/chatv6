package com.github.BurnieLiang.chatv6.app.controller;

import com.github.BurnieLiang.chatv6.model.entity.Reply;
import com.github.BurnieLiang.chatv6.model.entity.Message;
import com.github.BurnieLiang.chatv6.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Boning Liang
 * @date 2020-09-14 09:56:22
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public Reply send(HttpServletRequest request) {
        Message msgVO = new Message();
        chatService.send(msgVO);
        log.error("error");
        return new Reply<>();
    }

}
