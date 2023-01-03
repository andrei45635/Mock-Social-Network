package com.example.lab6_socialnetwork_gui.dto;

import java.util.Objects;

public class MessageUserDTO {
    private final String firstName;
    private final String lastName;

    public MessageUserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageUserDTO)) return false;
        MessageUserDTO that = (MessageUserDTO) o;
        return getFirstName().equals(that.getFirstName()) && getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
