package com.example.lab6_socialnetwork_gui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long> {
    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private String passwd;
    private int age;
    private List<User> friends;

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User(int ID, String firstName, String lastName, String email, String passwd, int age) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwd = passwd;
        this.age = age;
        this.friends = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getID() == user.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    public String toString() {
        return "User{ID=" + this.ID + ", firstName='" + this.firstName + "', lastName='" + this.lastName + "', email='" + this.email + "', passwd='" + this.passwd + "', age=" + this.age + "}";
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public int getAge() {
        return this.age;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriends() {
        return this.friends;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }
}
