package com.example.lab6_socialnetwork_gui.validators;

import com.example.lab6_socialnetwork_gui.domain.User;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    public UserValidator() {
    }

    @Override
    public void validate(User entity) throws ValidatorException {
        String errors = "";
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
        Matcher email_matcher = email_pattern.matcher(entity.getEmail());
        boolean matchFound = email_matcher.find();
        if (entity.getID() != entity.getID()) {
           errors += "ID has to be an integer\n";
        }
        if (Objects.equals(entity.getFirstName(), "")) {
            errors += "First Name can't be null or empty\n";
        }
        if (Objects.equals(entity.getLastName(), "")) {
            errors += "Last Name can't be null or empty\n";
        }
        if (Objects.equals(entity.getEmail(), "") || !matchFound) {
           errors += "Email can't be null, empty or anything ending in something else than *@.*\n";
        }
        if (Objects.equals(entity.getPasswd(), "") || entity.getPasswd().length() <= 8 || Objects.equals(entity.getPasswd(), " ")) {
           errors += "Password can't be null, empty or have less than 8 characters\n";
        }
        if (entity.getAge() < 13 || entity.getAge() > 99) {
            errors += "You have to be over 13 and a human to use this app.\n";
        }
        if(errors.length() > 0){
            throw new ValidatorException(errors);
        }
    }
}
