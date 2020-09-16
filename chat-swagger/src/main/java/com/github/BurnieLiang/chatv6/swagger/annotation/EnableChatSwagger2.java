package com.github.BurnieLiang.chatv6.swagger.annotation;

import com.github.BurnieLiang.chatv6.swagger.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Boning Liang
 * @date 2020-09-16 16:06:45
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerConfiguration.class})
public @interface EnableChatSwagger2 {
}
