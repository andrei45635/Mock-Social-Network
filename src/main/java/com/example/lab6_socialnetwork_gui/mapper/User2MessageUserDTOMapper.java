package com.example.lab6_socialnetwork_gui.mapper;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.dto.MessageUserDTO;

import java.util.ArrayList;
import java.util.List;

public class User2MessageUserDTOMapper {

    public MessageUserDTO convert(User user){
        return new MessageUserDTO(user.getFirstName(), user.getLastName());
    }

    public List<MessageUserDTO> convert(List<User> users){
        List<MessageUserDTO> messageUserDTOList = new ArrayList<>();
        for(User user : users){
            messageUserDTOList.add(convert(user));
        }
        return messageUserDTOList;
    }
}
