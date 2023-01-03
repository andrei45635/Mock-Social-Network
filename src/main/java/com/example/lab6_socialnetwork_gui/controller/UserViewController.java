package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.FriendshipStatus;
import com.example.lab6_socialnetwork_gui.domain.Message;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.dto.FriendUserDTO;
import com.example.lab6_socialnetwork_gui.dto.MessageDTO;
import com.example.lab6_socialnetwork_gui.dto.MessageUserDTO;
import com.example.lab6_socialnetwork_gui.dto.UserDTO;
import com.example.lab6_socialnetwork_gui.mapper.Message2MessageDTOMapper;
import com.example.lab6_socialnetwork_gui.mapper.User2FriendUserDTOMapper;
import com.example.lab6_socialnetwork_gui.mapper.User2MessageUserDTOMapper;
import com.example.lab6_socialnetwork_gui.mapper.User2UserDTOMapper;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.service.Service;
import com.example.lab6_socialnetwork_gui.utils.event.UserEntityChangeEvent;
import com.example.lab6_socialnetwork_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserViewController implements Observer<UserEntityChangeEvent> {
    @FXML
    private TableView<MessageUserDTO> messageUserTableView;
    //@FXML
    //private ListView<MessageDTO> messageList;
    @FXML
    private ListView<String> messageList;
    @FXML
    private TableColumn<MessageUserDTO, String> firstNameColumnMessage;
    @FXML
    private TableColumn<MessageUserDTO, String> lastNameColumnMessage;
    @FXML
    private Button sendMessageButton;
    @FXML
    private TextField messageTF;
    @FXML
    private Label messageTextLabel;
    //Table Views
    @FXML
    private TableView<UserDTO> friendsTableView;
    @FXML
    private TableView<UserDTO> requestsTableView;
    @FXML
    private TableView<FriendUserDTO> searchFriendTableView;

    //Service
    private Service service;

    //The logged-in user
    private User loggedInUser;

    private final ObservableList<UserDTO> friendsModel = FXCollections.observableArrayList();
    private final ObservableList<FriendUserDTO> searchFriendsModel = FXCollections.observableArrayList();
    private final ObservableList<UserDTO> friendRequestModel = FXCollections.observableArrayList();
    private final ObservableList<MessageUserDTO> messageUserModel = FXCollections.observableArrayList();
    //private final ObservableList<MessageDTO> messageDTOSModel = FXCollections.observableArrayList();
    private final ObservableList<String> messageDTOSModel = FXCollections.observableArrayList();

    private final User2UserDTOMapper userDTOMapper = new User2UserDTOMapper(new FriendshipDBRepo());
    private final User2FriendUserDTOMapper user2FriendUserDTOMapper = new User2FriendUserDTOMapper();
    private final User2MessageUserDTOMapper user2MessageUserDTOMapper = new User2MessageUserDTOMapper();
    private final Message2MessageDTOMapper message2MessageDTOMapper = new Message2MessageDTOMapper();

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
    private TableColumn<UserDTO, FriendshipStatus> friendStatusColumn;
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
    private TableColumn<UserDTO, FriendshipStatus> requestStatusColumn;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;

    //Search page featuring the list of filtered users by the first and last names
    @FXML
    private TableColumn<FriendUserDTO, Integer> searchIDColumn;
    @FXML
    private TableColumn<FriendUserDTO, String> searchFirstNameColumn;
    @FXML
    private TableColumn<FriendUserDTO, String> searchLastNameColumn;
    @FXML
    private TextField searchFirstNameTF;
    @FXML
    private TextField searchLastNameTF;

    @FXML
    private void onRemoveFriendClick(ActionEvent actionEvent) throws IOException {
        UserDTO user = friendsTableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            for (Friendship fr : service.getAllFriendsService()) {
                if (user.getID() == fr.getIdU1() && loggedInUser.getID() == fr.getIdU2()) {
                    service.deleteFriendService(user.getID(), loggedInUser.getID());
                    System.out.println("remove");
                } else if (user.getID() == fr.getIdU2() && loggedInUser.getID() == fr.getIdU1()) {
                    service.deleteFriendService(loggedInUser.getID(), user.getID());
                }
            }
        }
        initModel();
        initSearchModel();
        initMessageModel();
    }

    @FXML
    private void onAcceptFriendReqClick(ActionEvent actionEvent) {
        UserDTO user = requestsTableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            for (Friendship fr : service.getAllFriendsService()) {
                if (user.getID() == fr.getIdU1() && loggedInUser.getID() == fr.getIdU2()) {
                    service.acceptFriendship(fr);
                } else if (user.getID() == fr.getIdU2() && loggedInUser.getID() == fr.getIdU1()) {
                    service.acceptFriendship(fr);
                }
            }
        }
        initModel();
        initSearchModel();
        initRequestsModel();
        initMessageModel();
    }

    @FXML
    private void onRejectReqClick(ActionEvent actionEvent) throws IOException {
        UserDTO user = requestsTableView.getSelectionModel().getSelectedItem();
        System.out.println(user);
        if (user != null) {
            for (Friendship fr : service.getAllFriendsService()) {
                if (user.getID() == fr.getIdU1() && loggedInUser.getID() == fr.getIdU2()) {
                    service.deleteFriendService(user.getID(), loggedInUser.getID());
                } else if (user.getID() == fr.getIdU2() && loggedInUser.getID() == fr.getIdU1()) {
                    service.deleteFriendService(loggedInUser.getID(), user.getID());
                }
            }
        }
        initRequestsModel();
        initSearchModel();
    }

    @FXML
    private void onSendFriendReqClick(ActionEvent actionEvent) throws IOException {
        FriendUserDTO user = searchFriendTableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            service.addFriendService(loggedInUser.getID(), user.getID());
        }
        if (user != null) {
            for (Friendship fr : service.getAllFriendsService()) {
                if (loggedInUser.getID() == fr.getIdU1() && user.getID() == fr.getIdU2()) {
                    fr.setStatus(FriendshipStatus.PENDING);
                } else if (loggedInUser.getID() == fr.getIdU2() && user.getID() == fr.getIdU1()) {
                    fr.setStatus(FriendshipStatus.PENDING);
                }
            }
        }
        initSearchModel();
        initRequestsModel();
    }

    @FXML
    private void onSendMessageButtonClick(ActionEvent actionEvent) {
        MessageUserDTO user = messageUserTableView.getSelectionModel().getSelectedItem();
        User found = null;
        if (user != null) {
            for(User u: service.getAllService()){
                if (u.getFirstName().equals(user.getFirstName()) && u.getLastName().equals(user.getLastName())){
                    found = u;
                }
            }
            List<Message> msgs = service.getMessagesForTwoFriends(loggedInUser, found);
            for(int i = 0; i < msgs.size(); i++){
                messageDTOSModel.setAll(msgs.get(i).getMessage());
                messageList.setItems(messageDTOSModel);
            }
            String message = messageTF.getText();
            assert found != null;
            service.addMessageService(loggedInUser.getID(), found.getID(), message);
//            messageDTOSModel.setAll(message2MessageDTOMapper.convert(msgs));
//            messageList.setItems(messageDTOSModel);
//            messageList.refresh();
//            String message = messageTF.getText();
//            assert found != null;
//            service.addMessageService(loggedInUser.getID(), found.getID(), message);
//            messageDTOSModel.setAll(message2MessageDTOMapper.convert(msgs));
//            messageList.setItems(messageDTOSModel);
//            System.out.printf(message);
//            messageList.refresh();
        }
    }

    @FXML
    private void onWithdrawFriendReq(ActionEvent actionEvent) {
        UserDTO user = requestsTableView.getSelectionModel().getSelectedItem();
        for (User u : service.getAllService()) {
            if (u.getID() == user.getID()) {
                for (Friendship fr : service.getAllFriendsService()) {
                    if (fr.getIdU1() == u.getID() || fr.getIdU2() == u.getID()) {
                        service.withdrawFriendReq(fr);
                    }
                }
            }
        }
        initRequestsModel();
        initSearchModel();
    }

    @Override
    public void update(UserEntityChangeEvent userEntityChangeEvent) {
        initModel();
        initSearchModel();
        initRequestsModel();
        initMessageModel();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
        initSearchModel();
        initRequestsModel();
        initMessageModel();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        friendStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        friendsTableView.setItems(friendsModel);

        reqIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        reqFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        System.out.println("random");
        reqLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        reqDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        requestStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        requestsTableView.setItems(friendRequestModel);
        firstNameTF.textProperty().addListener(f -> userFilters());
        lastNameTF.textProperty().addListener(f -> userFilters());

        searchIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        searchFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        searchLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        searchFriendTableView.setItems(searchFriendsModel);

        searchFirstNameTF.textProperty().addListener(f -> userFilters());
        searchLastNameTF.textProperty().addListener(f -> userFilters());

        firstNameColumnMessage.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnMessage.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        messageUserTableView.setItems(messageUserModel);
        messageList.setItems(messageDTOSModel);
    }

    public void initMessageModel() {
        List<User> users = service.getAllService();
        List<User> friends = new ArrayList<>();
        for (User u : users) {
            if (!u.equals(loggedInUser) && service.isFriendsWith(u, loggedInUser)) {
                friends.add(u);
            }
        }
        messageUserModel.setAll(user2MessageUserDTOMapper.convert(friends));
    }

    public void initRequestsModel() {
        List<User> users = service.getAllService();
        List<Friendship> friendships = service.getAllFriendsService();
        List<UserDTO> friendDTOS = new ArrayList<>();
        for (User u : users) {
            for (Friendship fr : friendships) {
                if (u.getID() == loggedInUser.getID() && loggedInUser.getID() == fr.getIdU1() && fr.getStatus() == FriendshipStatus.PENDING) {
                    User friend = service.findOneService((int) fr.getIdU2());
                    UserDTO friendDTO = userDTOMapper.convert(friend);
                    friendDTO.setStatus(FriendshipStatus.PENDING);
                    friendDTOS.add(friendDTO);
                } else if (u.getID() == loggedInUser.getID() && loggedInUser.getID() == fr.getIdU2() && fr.getStatus() == FriendshipStatus.PENDING) {
                    User friend = service.findOneService((int) fr.getIdU1());
                    UserDTO friendDTO = userDTOMapper.convert(friend);
                    friendDTO.setStatus(FriendshipStatus.PENDING);
                    friendDTOS.add(friendDTO);
                }
            }
        }
        friendRequestModel.setAll(friendDTOS);
    }

    public void initSearchModel() {
        List<User> users = service.getAllService();
        List<User> others = new ArrayList<>();
        for (User o : users) {
            if (!o.equals(loggedInUser) && !service.isFriendsWith(o, loggedInUser)) {
                others.add(o);
            }
        }
        searchFriendsModel.setAll(user2FriendUserDTOMapper.convert(others));
    }

    private void initModel() {
        service.findUserFriends(loggedInUser);
        List<User> friends = new ArrayList<>();
        for (Friendship fr : service.getAllFriendsService()) {
            if (fr.getIdU1() == loggedInUser.getID() && fr.getStatus() == FriendshipStatus.ACCEPTED) {
                User friend = service.findOneService((int) fr.getIdU2());
                friends.add(friend);
            } else if (fr.getIdU2() == loggedInUser.getID() && fr.getStatus() == FriendshipStatus.ACCEPTED) {
                User friend = service.findOneService((int) fr.getIdU1());
                friends.add(friend);
            }
        }
        friendsModel.setAll(userDTOMapper.convert(friends));
    }

    public void setWelcomeText(String name) {
        welcomeText.setText("Good day " + name);
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private void userFilters() {
        String firstName = searchFirstNameColumn.getText();
        String lastName = searchLastNameTF.getText();
        Predicate<FriendUserDTO> firstNamePredicate = friendUserDTO -> friendUserDTO.getFirstName().contains(firstName);
        Predicate<FriendUserDTO> lastNamePredicate = friendUserDTO -> friendUserDTO.getLastName().contains(lastName);
        Predicate<FriendUserDTO> predicateResult = firstNamePredicate.or(lastNamePredicate);
        List<User> users = service.getAllService();
        List<FriendUserDTO> friendUsers = user2FriendUserDTOMapper.convert(users);
        searchFriendsModel.setAll(friendUsers.stream().filter(predicateResult).collect(Collectors.toList()));
    }

    @FXML
    private void onClickOpenChat(ActionEvent actionEvent) throws IOException {
        MessageUserDTO user = messageUserTableView.getSelectionModel().getSelectedItem();
        User clickedUser = null;
        if (user != null) {
            for (User u : service.getAllService()) {
                if (u.getFirstName().equals(user.getFirstName()) && u.getLastName().equals(user.getLastName())) {
                    clickedUser = u;
                }
            }
        }
        ///com/example/lab6_socialnetwork_gui/
        System.out.println(getClass().getResource("resources/com/example/lab6_socialnetwork_gui/chat-window.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab6_socialnetwork_gui/chat-window.fxml"));
        Parent root = loader.load();
        ChatWindowController chatWindowController = loader.getController();
        chatWindowController.setLoggedInUser(loggedInUser);
        chatWindowController.setClickedUser(clickedUser);
        chatWindowController.setChatLabel(loggedInUser, clickedUser);
        chatWindowController.setService(service);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Hello!");
        stage.show();
    }
}