package com.example.lab6_socialnetwork_gui.validators;

public interface Validator<T> {
    void validate(T var1) throws ValidatorException;
}
