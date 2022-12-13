package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label errorLabel;
    private Service service;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwdTF;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void onLoginPress(ActionEvent actionEvent) throws IOException {
        String email = emailTF.getText();
        String passwd = passwdTF.getText();
        if (!service.checkUserExistsService(email, passwd)) {
            errorLabel.setText("Invalid Credentials");
            return;
        }

        User loggedInUser = service.findLoggedInUser(email, passwd);
        String name = loggedInUser.getFirstName() + " " + loggedInUser.getLastName();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab6_socialnetwork_gui/user-friends-view.fxml"));
        Parent root = loader.load();
        UserViewController userViewController = loader.getController();
        userViewController.setLoggedInUser(loggedInUser);
        userViewController.setService(service);
        userViewController.setWelcomeText(name);
        System.out.println();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 600));
        stage.setTitle("Hello!");
        stage.show();

        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void onRegisterClick(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage) registerButton.getScene().getWindow();
        thisStage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/lab6_socialnetwork_gui/register-view.fxml"));
        Parent root1 = loader.load();
        RegisterController registerController = loader.getController();
        registerController.setService(service);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 400, 350));
        stage.setTitle("Hello!");
        stage.show();
    }
}