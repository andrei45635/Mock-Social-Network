package com.example.lab6_socialnetwork_gui.utils.observer;

import com.example.lab6_socialnetwork_gui.utils.event.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
