package com.github.BurnieLiang.chatv6.app.controller;

import com.github.BurnieLiang.chatv6.model.entity.Reply;
import com.github.BurnieLiang.chatv6.util.WebUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Boning Liang
 * @date 2020-09-16 15:46:17
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestHttpController {

    @GetMapping("/ip")
    @ApiOperation(value = "获取ip地址")
    public Reply ip(HttpServletRequest request) {
        return new Reply<>(WebUtil.getIPAddress(request));
    }

}
