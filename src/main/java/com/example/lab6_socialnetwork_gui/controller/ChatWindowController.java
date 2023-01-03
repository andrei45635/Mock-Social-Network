package com.example.lab6_socialnetwork_gui.controller;

import com.example.lab6_socialnetwork_gui.domain.Message;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class ChatWindowController {
    @FXML
    private Label chatLabel;
    @FXML
    private TextFlow messageTextFlow;
    @FXML
    private TextField messageBoxTF;
    @FXML
    private Button sendMessageButton;

    private Service service;
    private User loggedInUser;
    private User clickedUser;

    public void setService(Service service){
        this.service = service;
    }

    public void setLoggedInUser(User loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    public void setClickedUser(User clickedUser){
        this.clickedUser = clickedUser;
    }

    public void setChatLabel(User loggedInUser, User clickedUser){
        chatLabel.setText("You (" + loggedInUser + ") are chatting with " + clickedUser);
    }

    @FXML
    public void initialize(){
        List<Message> messages = service.getMessagesForTwoFriends(loggedInUser, clickedUser);
        for(Message msg: messages){
            Text text = new Text(msg.getMessage());
            messageTextFlow.getChildren().add(text);
        }
    }

    public void onClickSendMessageButton(ActionEvent actionEvent) {

    }
}
