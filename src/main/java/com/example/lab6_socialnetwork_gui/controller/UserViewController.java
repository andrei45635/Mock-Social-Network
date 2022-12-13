package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.domain.dto.UserDTO;
import com.example.lab6_socialnetwork_gui.domain.mapper.User2UserDTOMapper;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.service.Service;
import com.example.lab6_socialnetwork_gui.utils.event.UserEntityChangeEvent;
import com.example.lab6_socialnetwork_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserViewController implements Observer<UserEntityChangeEvent> {
    @FXML
    private TableView<UserDTO> friendsTableView;
    @FXML
    private TableView<UserDTO> requestsTableView;

    //Service (might refactor later)
    private Service service;

    //The logged-in user
    private User loggedInUser;

    private final ObservableList<UserDTO> friendsModel = FXCollections.observableArrayList();

    private final User2UserDTOMapper userDTOMapper = new User2UserDTOMapper(new FriendshipDBRepo());

    //Main page featuring the friends of the logged-in user
    @FXML
    private TableColumn<UserDTO, Integer> idColumn;
    @FXML
    private TableColumn<UserDTO, String> firstNameColumn;
    @FXML
    private TableColumn<UserDTO, String> lastNameColumn;
    @FXML
    private TableColumn<UserDTO, LocalDateTime> dateColumn;
    @FXML
    private Button removeFriendButton;
    @FXML
    private Label welcomeText;

    //Requests page featuring the logged-in user's requests
    @FXML
    private TableColumn<UserDTO, Integer> reqIDColumn;
    @FXML
    private TableColumn<UserDTO, String> reqFirstNameColumn;
    @FXML
    private TableColumn<UserDTO, String> reqLastNameColumn;
    @FXML
    private TableColumn<UserDTO, LocalDateTime> reqDateColumn;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private Button acceptFriendRequestButton;
    @FXML
    private Button rejectFriendRequestButton;

    @FXML
    private void onRemoveFriendClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onAcceptFriendReqClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onRejectReqClick(ActionEvent actionEvent) {
    }

    @Override
    public void update(UserEntityChangeEvent userEntityChangeEvent) {
        initModel();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("First Name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("Last Name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        friendsTableView.setItems(friendsModel);
    }

    private void initModel() {
        service.findUserFriends(loggedInUser);
        for(User u: loggedInUser.getFriends()){
            System.out.println(u);
        }
        List<User> friends = new ArrayList<>(loggedInUser.getFriends());
        friendsModel.setAll(userDTOMapper.convert(friends));
    }

    public void setWelcomeText(String name) {
        welcomeText.setText("Good day " + name);
    }

    public void setLoggedInUser(User loggedInUser) {
        System.out.println(loggedInUser);
        this.loggedInUser = loggedInUser;
    }
}