package com.example.lab6_socialnetwork_gui.repo.memory;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.validators.Validator;

public class UserMemoryRepository extends MemoryRepo<Long, User> {
    public UserMemoryRepository(Validator<User> validator) {
        super(validator);
    }
}
