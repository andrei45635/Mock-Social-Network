package com.example.lab6_socialnetwork_gui.domain;

import java.io.Serializable;
public class Entity<ID> implements Serializable {

    private static final long serialVersionUID = 30011971L;

    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
