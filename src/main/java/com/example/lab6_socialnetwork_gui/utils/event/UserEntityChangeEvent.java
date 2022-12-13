package com.example.lab6_socialnetwork_gui.utils.event;

import com.example.lab6_socialnetwork_gui.domain.User;

public class UserEntityChangeEvent implements Event {
    private ChangeEventType type;

    private User old_data;

    private User data;

    public UserEntityChangeEvent(ChangeEventType type, User data) {
        this.type = type;
        this.data = data;
    }

    public UserEntityChangeEvent(ChangeEventType type, User old_data, User data) {
        this.type = type;
        this.old_data = old_data;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public User getOld_data() {
        return old_data;
    }

    public User getData() {
        return data;
    }
}
