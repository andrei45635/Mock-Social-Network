package com.example.lab6_socialnetwork_gui.mapper;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.dto.FriendUserDTO;

import java.util.ArrayList;
import java.util.List;

public class User2FriendUserDTOMapper {
    public FriendUserDTO convert(User user){
        return new FriendUserDTO(user.getID(), user.getFirstName(), user.getLastName());
    }

    public List<FriendUserDTO> convert(List<User> users){
        List<FriendUserDTO> friendUserDTOS = new ArrayList<>();
        for(User user : users){
            friendUserDTOS.add(convert(user));
        }
        return friendUserDTOS;
    }
}
