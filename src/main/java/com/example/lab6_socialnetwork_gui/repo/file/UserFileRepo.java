package com.example.lab6_socialnetwork_gui.repo.file;

import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.validators.Validator;
import com.example.lab6_socialnetwork_gui.validators.ValidatorException;

import java.io.IOException;
import java.util.List;

public class UserFileRepo extends AbstractFileRepo<Long, User> {
    public UserFileRepo(String fileName, Validator<User> validator) throws IOException {
        super(fileName, validator);
        try{
            loadData();
        } catch (IOException e){
            throw new IOException(e);
        } catch (ValidatorException ve){
            System.out.println(ve.getMessage());
        }
    }

    @Override
    public User save(User entity) throws IOException{
        User e = super.save(entity);
        if (e == null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public boolean delete(User user) throws IOException{
        boolean ret = super.delete(user);
        if(ret){
            writeToFile();
        }
        return false;
    }

    @Override
    public User update(User user) throws IOException{
        if(super.update(user) != null){
            writeToFile();
        } else return null;
        return user;
    }

    @Override
    public User extractEntity(List<String> attrs) throws ValidatorException {
        return new User(Integer.parseInt(attrs.get(0)), attrs.get(1), attrs.get(2), attrs.get(3), attrs.get(4), Integer.parseInt(attrs.get(5)));
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getID() + ";" + entity.getFirstName() + ";" + entity.getLastName() + ";" + entity.getEmail() + ";" + entity.getPasswd() + ";" + entity.getAge();
    }
}
