package com.example.lab6_socialnetwork_gui.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserDTO {
    private final int ID;
    private String firstName;
    private String lastName;

    private LocalDateTime date;

    public UserDTO(int ID, String firstName, String lastName, LocalDateTime date) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                '}';
    }
}