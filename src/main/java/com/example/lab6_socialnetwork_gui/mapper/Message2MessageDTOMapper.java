package com.example.lab6_socialnetwork_gui.mapper;

import com.example.lab6_socialnetwork_gui.domain.Message;
import com.example.lab6_socialnetwork_gui.dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;

public class Message2MessageDTOMapper {
    public MessageDTO convert (Message msg){
        return new MessageDTO(msg.getMessage());
    }

    public List<MessageDTO> convert(List<Message> msgs){
        List<MessageDTO> msgDTOs = new ArrayList<>();
        for(Message mg: msgs){
            msgDTOs.add(convert(mg));
        }
        return msgDTOs;
    }
}
