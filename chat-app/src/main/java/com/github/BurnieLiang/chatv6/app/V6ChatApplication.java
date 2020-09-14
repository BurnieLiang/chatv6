package com.github.BurnieLiang.chatv6.app;

import com.github.BurnieLiang.chatv6.bootjavafx.AbstractJavaFxApplicationSupport;
import com.github.BurnieLiang.chatv6.app.views.LoginView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Boning Liang
 * @date 2020-09-11 13:46:25
 */
@SpringBootApplication(scanBasePackages = {"com.github.BurnieLiang.chatv6.*"})
public class V6ChatApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(V6ChatApplication.class, LoginView.class, args);
    }

}
