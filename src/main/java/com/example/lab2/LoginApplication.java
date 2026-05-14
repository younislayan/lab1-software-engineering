package com.example.lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginApplication extends Application {
    private static LoginSecurityManager securityManager;

    @Override
    public void init() {
        List<String> args = getParameters().getRaw();

        if (args.size() != 2) {
            System.out.println("Please enter two arguments: n and t");
            securityManager = new LoginSecurityManager(3, 10);
            return;
        }

        int maxAttempts = Integer.parseInt(args.get(0));
        int blockTimeSeconds = Integer.parseInt(args.get(1));

        securityManager = new LoginSecurityManager(maxAttempts, blockTimeSeconds);
    }

    public static LoginSecurityManager getSecurityManager() {
        return securityManager;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}