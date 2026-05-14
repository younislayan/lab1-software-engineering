package com.example.lab2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private ArrayList<User> users;
    private LoginSecurityManager securityManager;

    @FXML
    public void initialize() {
        users = UsersReader.readUsers();
        securityManager = LoginApplication.getSecurityManager();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = findUserByUsername(username);

        if (user == null) {
            errorLabel.setText("user or password do not match");
            return;
        }

        if (user.getPassword().equals(password)) {
            BlockedCheckThread blockedCheckThread =
                    new BlockedCheckThread(user, securityManager);

            blockedCheckThread.start();

            try {
                blockedCheckThread.join();
            } catch (InterruptedException e) {
                errorLabel.setText("Login interrupted");
                return;
            }

            if (blockedCheckThread.isBlocked()) {
                errorLabel.setText("User is blocked. Please try again later.");
                return;
            }

            user.setFailedAttempts(0);
            openWelcomeScreen();
        } else {
            if (securityManager.isUserBlocked(user)) {
                errorLabel.setText("User is blocked. Please try again later.");
                return;
            }

            FailedLoginThread failedLoginThread =
                    new FailedLoginThread(user, securityManager);

            failedLoginThread.start();

            try {
                failedLoginThread.join();
            } catch (InterruptedException e) {
                errorLabel.setText("Login interrupted");
                return;
            }

            if (user.isBlocked()) {
                errorLabel.setText("Too many failed attempts. User is blocked.");
            } else {
                errorLabel.setText("user or password do not match. Remaining attempts: "
                        + securityManager.getRemainingAttempts(user));
            }
        }
    }

    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }

        return null;
    }

    private void openWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}