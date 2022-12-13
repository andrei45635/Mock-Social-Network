package com.example.lab6_socialnetwork_gui.utils.observer;

import com.example.lab6_socialnetwork_gui.utils.event.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> observer);

    void removeObserver(Observer<E> observer);

    void notifyObservers(E t);
}
