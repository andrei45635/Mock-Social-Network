package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.service.Service;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UserViewController {
    @FXML
    private Tab requestsPage;
    @FXML
    private Tab mainPage;
    //The service for both the user and the friendship (might refactor later)
    private Service service;
    // The main page, featuring all the logged-in user's friends
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<User> friendTableView;
    @FXML
    private Button removeFriendButton;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    
    //The requests page, featuring all the friend requests
    @FXML
    private TableView<User>  requestTableView;
    @FXML
    private TableColumn<User, Integer> requestID;
    @FXML
    private TableColumn<User, String> firstNameRequestColumn;
    @FXML
    private TableColumn<User, String> lastNameRequestColumn;
    @FXML
    private TextField firstNameRequestTF;
    @FXML
    private Label firstNameRequestLabel;
    @FXML
    private TextField lastNameRequestTF;
    @FXML
    private Label lastNameRequestLabel;
    @FXML
    private Button acceptRequestButton;
    @FXML
    private Button rejectFriendRequestButton;

    public void setWelcomeText(String name){
        welcomeText.setText("Good day, " + name);
    }

    public void setService(Service service){
        this.service = service;
    }

    //Method in the Main page to remove a friend the user selected in the Friends list
    @FXML
    private void onClickRemoveFriend(ActionEvent actionEvent) {

    }

    //Method in the Requests page to accept a friend request
    @FXML
    public void onAcceptRequestPress(ActionEvent actionEvent) {

    }

    //Method in the Requests page to reject a friend request
    @FXML
    public void onRejectFriendRequestPress(ActionEvent actionEvent) {

    }

    @FXML
    private void onMainPageSelect(Event event) {

    }

    @FXML
    private void onRequestsPageSelect(Event event) {
    }
}
