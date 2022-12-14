package com.example.lab6_socialnetwork_gui.dto;

import com.example.lab6_socialnetwork_gui.domain.FriendshipStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserDTO {
    private final int ID;
    private String firstName;
    private String lastName;
    private LocalDateTime date;
    private FriendshipStatus status;

    public FriendshipStatus getStatus() {
        return status;
    }

    public UserDTO(int ID, String firstName, String lastName, LocalDateTime date, FriendshipStatus status) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.status = status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return ID == userDTO.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}