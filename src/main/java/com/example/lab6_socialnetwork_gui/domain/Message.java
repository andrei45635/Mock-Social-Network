package com.example.lab6_socialnetwork_gui.domain;

import java.util.Objects;

public class Message extends Entity<Long> {
    private int senderID;
    private int receiverID;
    private String message;

    public Message(int senderID, int receiverID, String message) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getSenderID() == message.getSenderID() && getReceiverID() == message.getReceiverID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSenderID(), getReceiverID());
    }
}
