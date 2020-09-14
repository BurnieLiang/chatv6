package com.github.BurnieLiang.chatv6.app.controller;

import com.github.BurnieLiang.chatv6.model.entity.Reply;
import com.github.BurnieLiang.chatv6.model.vo.MsgVO;
import com.github.BurnieLiang.chatv6.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Boning Liang
 * @date 2020-09-14 09:56:22
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public Reply send() {
        MsgVO msgVO = new MsgVO();
        chatService.send(msgVO);
        return new Reply<>();
    }

}
