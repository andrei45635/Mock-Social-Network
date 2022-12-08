package com.example.lab6_socialnetwork_gui.validators;

import com.example.lab6_socialnetwork_gui.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {
    public FriendshipValidator() {
    }

    @Override
    public void validate(Friendship entity) throws ValidatorException {
        String errors = "";
        if(entity.getIdU1() < 0 || entity.getIdU2() < 0){
            errors += "Invalid ID\n";
        }
        if(errors.length() > 0){
            throw new ValidatorException(errors);
        }
    }
}
