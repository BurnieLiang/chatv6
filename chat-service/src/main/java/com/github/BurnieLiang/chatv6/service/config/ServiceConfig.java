package com.github.BurnieLiang.chatv6.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Boning Liang
 * @date 2020-09-16 11:41:53
 */
@Configuration
public class ServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
