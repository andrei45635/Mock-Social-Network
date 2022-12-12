package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.HelloApplication;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class LoginController {
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
        System.out.println(email);
        System.out.println(passwd);
        System.out.println(service.checkUserExistsService(email, passwd));
        if (!service.checkUserExistsService(email, passwd)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Epic Fail");
            alert.setContentText("Invalid credentials.");
            alert.show();
            return;
        }

        String name = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../user-view.fxml"));
        Parent root1 = loader.load();
        UserViewController userViewController = loader.getController();
        userViewController.setService(service);
        for(User u: service.getAllService()){
            if(Objects.equals(u.getEmail(), email) && Objects.equals(u.getPasswd(), passwd)){
                name = u.getFirstName() + u.getLastName();
                break;
            }
        }
        userViewController.setWelcomeText(name);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 400, 350));
        stage.setTitle("Hello!");
        stage.show();

        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void onRegisterClick(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage) registerButton.getScene().getWindow();
        thisStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../register-view.fxml"));
        Parent root1 = loader.load();
        RegisterController registerController = loader.getController();
        registerController.setService(service);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 400, 350));
        stage.setTitle("Hello!");
        stage.show();
    }
}