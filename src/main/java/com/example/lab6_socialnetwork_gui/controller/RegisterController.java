package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.service.Service;
import com.example.lab6_socialnetwork_gui.validators.ValidatorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RegisterController {
    @FXML
    private Button registerButton;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField ageTF;
    @FXML
    private TextField passwdTF;
    private Service service;

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    private void onRegisterButtonPress(ActionEvent actionEvent) throws IOException {
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String name = firstName + " " + lastName;
        String email = emailTF.getText();
        String passwd = passwdTF.getText();
        int age = Integer.parseInt(ageTF.getText());
        List<User> users = service.getAllService();
        int lastID = users.get(users.size() - 1).getID();
        try{
            service.addUserService(lastID + 1, firstName, lastName, email, passwd, age);

        } catch (ValidatorException ve){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Epic Fail");
            alert.setContentText(ve.getMessage());
            alert.show();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab6_socialnetwork_gui/user-friends-view.fxml"));
        Parent root1 = loader.load();
        UserViewController userViewController = loader.getController();
        userViewController.setService(service);
        userViewController.setWelcomeText(name);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 600, 600));
        stage.setTitle("Hello!");
        stage.show();

        Stage thisStage = (Stage) registerButton.getScene().getWindow();
        thisStage.close();
    }
}
