package com.example.lab6_socialnetwork_gui;

import com.example.lab6_socialnetwork_gui.controller.LoginController;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.repo.database.MessageDBRepo;
import com.example.lab6_socialnetwork_gui.repo.database.UserDBRepo;
import com.example.lab6_socialnetwork_gui.service.Service;
import com.example.lab6_socialnetwork_gui.validators.UserValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(new Service(new UserValidator(), new UserDBRepo(), new FriendshipDBRepo(), new MessageDBRepo()));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}