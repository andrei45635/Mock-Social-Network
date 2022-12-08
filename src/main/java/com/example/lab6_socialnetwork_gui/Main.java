package com.example.lab6_socialnetwork_gui;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.repo.database.UserDBRepo;
import com.example.lab6_socialnetwork_gui.service.Service;
import com.example.lab6_socialnetwork_gui.user_interface.UserInterface;
import com.example.lab6_socialnetwork_gui.validators.FriendshipValidator;
import com.example.lab6_socialnetwork_gui.validators.UserValidator;
import com.example.lab6_socialnetwork_gui.validators.Validator;


import java.io.IOException;


public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException {
        Validator<User> validator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();
        UserDBRepo repo = new UserDBRepo();
        FriendshipDBRepo friendships = new FriendshipDBRepo();
//        String fileUsersName = "src\\users.csv";
//        String fileFriendsName = "src\\friends.csv";
//        UserFileRepo repo;
//        try {
//            repo = new UserFileRepo(fileUsersName, validator);
//        } catch (IOException e) {
//            throw new IOException(e);
//        }
//        FriendshipFileRepo friendships;
//        try {
//            friendships = new FriendshipFileRepo(fileFriendsName, friendshipValidator);
//        } catch (IOException e) {
//            throw new IOException(e);
//        }
        Service service = new Service(validator, repo, friendships);
        UserInterface ui = new UserInterface(service);
        ui.start();
    }
}
