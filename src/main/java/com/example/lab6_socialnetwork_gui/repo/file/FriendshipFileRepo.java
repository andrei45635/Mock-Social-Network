package com.example.lab6_socialnetwork_gui.repo.file;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.validators.Validator;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class FriendshipFileRepo extends AbstractFileRepo<Long, Friendship> {
    public FriendshipFileRepo(String fileName, Validator<Friendship> validator) throws IOException{
        super(fileName, validator);
        try{
            loadData();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Friendship save(Friendship entity) throws IOException{
        Friendship e = super.save(entity);
        if (e == null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public boolean delete(Friendship user) throws IOException{
        boolean ret = super.delete(user);
        if (ret) {
            writeToFile();
        }
        return false;
    }

    @Override
    public Friendship update(Friendship user) throws IOException{
        if (super.update(user) != null) {
            writeToFile();
        } else return null;
        return user;
    }

    @Override
    public Friendship extractEntity(List<String> attrs) {
        return new Friendship(Integer.parseInt(attrs.get(0)), Integer.parseInt(attrs.get(1)), LocalDateTime.parse(attrs.get(2)));
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getIdU1()+";"+entity.getIdU2()+";"+entity.getDate();
    }
}
