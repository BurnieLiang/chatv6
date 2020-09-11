package com.github.BurnieLiang.chatv6.app;

import com.github.BurnieLiang.chatv6.bootjavafx.AbstractJavaFxApplicationSupport;
import com.github.BurnieLiang.chatv6.app.views.LoginView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Boning Liang
 * @date 2020-09-11 13:46:25
 */
@SpringBootApplication
public class V6ChatApplication extends AbstractJavaFxApplicationSupport {

//    @Override
//    public void start(Stage stage) throws Exception {
//        ResourceBundle bundle = ResourceBundle.getBundle("strings", Locale.getDefault());
//
//        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"), bundle);
//        stage.setTitle("Hello World");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }


//    public static void main(String[] args) {
//        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
//        launch(args);
//    }



    public static void main(String[] args) {
        launch(V6ChatApplication.class, LoginView.class, args);
    }

}
