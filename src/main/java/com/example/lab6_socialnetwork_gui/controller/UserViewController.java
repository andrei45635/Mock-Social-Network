package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.domain.dto.UserDTO;
import com.example.lab6_socialnetwork_gui.domain.mapper.User2UserDTOMapper;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserViewController {
    @FXML
    private Tab requestsPage;
    @FXML
    private Tab mainPage;

    private final ObservableList<UserDTO> userModel = FXCollections.observableArrayList();

    private final User2UserDTOMapper userDTOMapper = new User2UserDTOMapper(new FriendshipDBRepo());

    //The service for both the user and the friendship (might refactor later)
    private Service service;

    // The main page, featuring all the logged-in user's friends
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<UserDTO> friendTableView;
    @FXML
    private Button removeFriendButton;
    @FXML
    private TableColumn<UserDTO, Integer> idColumn;
    @FXML
    private TableColumn<UserDTO, String> firstNameColumn;
    @FXML
    private TableColumn<UserDTO, String> lastNameColumn;

    //The requests page, featuring all the friend requests
    @FXML
    private TableView<UserDTO> requestTableView;
    @FXML
    private TableColumn<UserDTO, Integer> requestID;
    @FXML
    private TableColumn<UserDTO, String> firstNameRequestColumn;
    @FXML
    private TableColumn<UserDTO, String> lastNameRequestColumn;
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

    public void setWelcomeText(String name) {
        welcomeText.setText("Good day, " + name);
    }

    public void setService(Service service) {
        this.service = service;
    }

    //Method in the Main page to remove a friend the user selected in the Friends list
    @FXML
    private void onRemoveFriendMainClick(ActionEvent actionEvent) {
    }

    //Method in the Requests page to reject a friend request
    @FXML
    private void onAcceptReqClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onRejectReqClick(ActionEvent actionEvent) {
    }
}
