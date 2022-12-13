package com.example.lab6_socialnetwork_gui.dto;

import java.util.Objects;

public class FriendUserDTO {
    private final int ID;
    private String firstName;
    private String lastName;

    public FriendUserDTO(int ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getID() {
        return ID;
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
        if (!(o instanceof FriendUserDTO)) return false;
        FriendUserDTO that = (FriendUserDTO) o;
        return getID() == that.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public String toString() {
        return "FriendUserDTO{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
