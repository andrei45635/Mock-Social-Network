package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.FriendshipStatus;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.dto.FriendUserDTO;
import com.example.lab6_socialnetwork_gui.dto.UserDTO;
import com.example.lab6_socialnetwork_gui.mapper.User2FriendUserDTOMapper;
import com.example.lab6_socialnetwork_gui.mapper.User2UserDTOMapper;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.repo.database.UserDBRepo;
import com.example.lab6_socialnetwork_gui.service.Service;
import com.example.lab6_socialnetwork_gui.utils.event.UserEntityChangeEvent;
import com.example.lab6_socialnetwork_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserViewController implements Observer<UserEntityChangeEvent> {
    //Table Views
    @FXML
    private TableView<UserDTO> friendsTableView;
    @FXML
    private TableView<UserDTO> requestsTableView;
    @FXML
    private TableView<FriendUserDTO> searchFriendTableView;

    //Service (might refactor later)
    private Service service;

    //The logged-in user
    private User loggedInUser;

    private final ObservableList<UserDTO> friendsModel = FXCollections.observableArrayList();

    private final ObservableList<FriendUserDTO> searchFriendsModel = FXCollections.observableArrayList();

    private final ObservableList<UserDTO> friendRequestModel = FXCollections.observableArrayList();

    private final User2UserDTOMapper userDTOMapper = new User2UserDTOMapper(new FriendshipDBRepo());

    private final User2FriendUserDTOMapper user2FriendUserDTOMapper = new User2FriendUserDTOMapper();

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

    //Search page featuring the list of filtered users by the first and last names
    @FXML
    private TableColumn<FriendUserDTO, Integer> searchIDColumn;
    @FXML
    private TableColumn<FriendUserDTO, String> searchFirstNameColumn;
    @FXML
    private TableColumn<FriendUserDTO, String> searchLastNameColumn;
    @FXML
    private Button sendFriendReqButton;
    @FXML
    private TextField searchFirstNameTF;
    @FXML
    private TextField searchLastNameTF;

    @FXML
    private void onRemoveFriendClick(ActionEvent actionEvent) throws IOException {
        UserDTO user = friendsTableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            service.deleteUserService(user.getID());
        }
    }

    @FXML
    private void onAcceptFriendReqClick(ActionEvent actionEvent) {
        UserDTO user = requestsTableView.getSelectionModel().getSelectedItem();
        if(user != null){
            for(Friendship fr: service.getAllFriendsService()){
                if(user.getID() == fr.getIdU1() || user.getID() == fr.getIdU2()){
                    service.acceptFriendship(fr);
                }
            }
        }
    }

    @FXML
    private void onRejectReqClick(ActionEvent actionEvent) throws IOException {
        UserDTO user = requestsTableView.getSelectionModel().getSelectedItem();
        if(user != null){
            for(Friendship fr: service.getAllFriendsService()){
                if(user.getID() == fr.getIdU1()){
                    service.deleteFriendService((int) fr.getIdU1(), user.getID());
                } else if(user.getID() == fr.getIdU2()){
                    service.deleteFriendService(user.getID(), (int) fr.getIdU2());
                }
            }
        }
    }

    @FXML
    private void onSendFriendReqClick(ActionEvent actionEvent) throws IOException {
        FriendUserDTO user = searchFriendTableView.getSelectionModel().getSelectedItem();
        if(user != null){
            service.addFriendService(loggedInUser.getID(), user.getID());
        }
    }

    @Override
    public void update(UserEntityChangeEvent userEntityChangeEvent) {
        initModel();
        initSearchModel();
        initRequestsModel();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
        initSearchModel();
        initRequestsModel();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        friendsTableView.setItems(friendsModel);

        reqIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        reqFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        reqLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        reqDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        requestsTableView.setItems(friendRequestModel);
        firstNameTF.textProperty().addListener(f -> userFilters());
        lastNameTF.textProperty().addListener(f -> userFilters());

        searchIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        searchFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        searchLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        searchFriendTableView.setItems(searchFriendsModel);

        searchFirstNameTF.textProperty().addListener(f -> userFilters());
        searchLastNameTF.textProperty().addListener(f -> userFilters());
    }

    public void initRequestsModel(){
        List<User> users = service.getAllService();
        List<Friendship> friendships = service.getAllFriendsService();
        List<User> requests = new ArrayList<>();
        for(User u: users){
            for(Friendship fr: friendships){
                if(u.getID() == loggedInUser.getID() && loggedInUser.getID() == fr.getIdU1() && fr.getStatus() == FriendshipStatus.PENDING){
                    requests.add(u);
                } else if (u.getID() == loggedInUser.getID() && loggedInUser.getID() == fr.getIdU2() && fr.getStatus() == FriendshipStatus.PENDING){
                    requests.add(u);
                }
            }
        }
        friendRequestModel.setAll(userDTOMapper.convert(requests));
    }

    public void initSearchModel(){
        List<User> users = new ArrayList<>(service.getAllService());
        searchFriendsModel.setAll(user2FriendUserDTOMapper.convert(users));
    }

    private void initModel() {
        service.findUserFriends(loggedInUser);
        List<User> friends = new ArrayList<>(loggedInUser.getFriends());
        friendsModel.setAll(userDTOMapper.convert(friends));
    }

    public void setWelcomeText(String name) {
        welcomeText.setText("Good day " + name);
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private void userFilters(){
        String firstName = searchFirstNameColumn.getText();
        String lastName = searchLastNameTF.getText();
        Predicate<FriendUserDTO> firstNamePredicate = friendUserDTO -> friendUserDTO.getFirstName().contains(firstName);
        Predicate<FriendUserDTO> lastNamePredicate = friendUserDTO -> friendUserDTO.getLastName().contains(lastName);
        Predicate<FriendUserDTO> predicateResult = firstNamePredicate.or(lastNamePredicate);
        List<User> users = service.getAllService();
        List<FriendUserDTO> friendUsers = user2FriendUserDTOMapper.convert(users);
        searchFriendsModel.setAll(friendUsers.stream().filter(predicateResult).collect(Collectors.toList()));
    }
}