package com.example.lab6_socialnetwork_gui.repo.memory;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.validators.Validator;

public class FriendshipMemoryRepo extends MemoryRepo<Long, Friendship> {
    public FriendshipMemoryRepo(Validator<Friendship> validator) {
        super(validator);
    }
}
