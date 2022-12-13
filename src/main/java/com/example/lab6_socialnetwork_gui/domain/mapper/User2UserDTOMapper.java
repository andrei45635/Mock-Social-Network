package com.example.lab6_socialnetwork_gui.domain.mapper;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.domain.dto.UserDTO;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;

import java.util.ArrayList;
import java.util.List;

public class User2UserDTOMapper {
    private final FriendshipDBRepo friendshipDBRepo;

    public User2UserDTOMapper(FriendshipDBRepo friendshipDBRepo) {
        this.friendshipDBRepo = friendshipDBRepo;
    }

    public UserDTO convert(User user){
        for(Friendship fr: friendshipDBRepo.getAll()){
            if(fr.getIdU1() == user.getID() || fr.getIdU2() == user.getID()){
                return new UserDTO(user.getID(), user.getFirstName(), user.getLastName(), fr.getDate());
            }
        }
        return null;
    }

    public List<UserDTO> convert(List<User> users){
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : users){
            userDTOList.add(convert(user));
        }
        return userDTOList;
    }
}
