package com.example.lab6_socialnetwork_gui.service;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.FriendshipStatus;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.repo.database.FriendshipDBRepo;
import com.example.lab6_socialnetwork_gui.repo.database.UserDBRepo;
import com.example.lab6_socialnetwork_gui.utils.event.ChangeEventType;
import com.example.lab6_socialnetwork_gui.utils.event.UserEntityChangeEvent;
import com.example.lab6_socialnetwork_gui.utils.observer.Observable;
import com.example.lab6_socialnetwork_gui.utils.observer.Observer;
import com.example.lab6_socialnetwork_gui.validators.Validator;
import com.example.lab6_socialnetwork_gui.validators.ValidatorException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Service class
 */
public class Service implements Observable<UserEntityChangeEvent> {
    private final Validator<User> validator;
    private UserDBRepo repo;
    private FriendshipDBRepo friendships;
    private List<Observer<UserEntityChangeEvent>> observers = new ArrayList<>();

    public Service(Validator<User> validator, UserDBRepo repo, FriendshipDBRepo friendships) {
        this.validator = validator;
        this.repo = repo;
        this.friendships = friendships;
    }

    /**
     * Returns a list with all the users
     * @return List of Users
     */
    public List<User> getAllService() {
        return repo.getAll();
    }

    /**
     * Returns a list with all the friends
     * @return List of Friends
     */
    public List<Friendship> getAllFriendsService() {
        return friendships.getAll();
    }

    /**
     * Checks if a user with the given email and passwd exists in the database
     * @param email  String
     * @param passwd String
     * @return true if the user exists, false otherwise
     */
    public boolean checkUserExistsService(String email, String passwd) {
        return repo.findUser(email, passwd);
    }

    /**
     * Returns a User based on the ID
     * @param id int
     * @return the User if the ID is valid, null otherwise
     */
    public User findOneService(int id) {
        return repo.findOne(id);
    }

    /**
     * Checks if two users are friends
     * @param u1 user 1
     * @param u2 user 2
     * @return true if the users are friends, false otherwise
     */
    public boolean isFriendsWith(User u1, User u2){
        for(Friendship fr: friendships.getAll()){
            if(fr.getIdU1() == u1.getID() && fr.getIdU2() == u2.getID()){
                return true;
            } else if(fr.getIdU1() == u2.getID() && fr.getIdU2() == u1.getID()){
                return true;
            }
        }
        return false;
    }

    /**
     * Finds all the friends of a user
     * loggeInUser.getFriends() (the list of friends) is filled with the friends of the aforementioned user
     * @param loggedInUser the user
     */
    public void findUserFriends(User loggedInUser) {
        for (Friendship fr : friendships.getAll()) {
            if (loggedInUser.getID() == fr.getIdU1()) {
                System.out.println(loggedInUser);
                User friend = repo.findOne((int) fr.getIdU2());
                System.out.println(friend);
                loggedInUser.getFriends().add(friend);
            } else if (loggedInUser.getID() == fr.getIdU2()) {
                User friend = repo.findOne((int) fr.getIdU1());
                loggedInUser.getFriends().add(friend);
            }
        }
    }

    /**
     * Finds a user based on their email and password
     * @param email String
     * @param passwd String
     * @return the User or null if the email and password don't exist
     */
    public User findLoggedInUser(String email, String passwd) {
        for (User u : repo.getAll()) {
            if (u.getEmail().equals(email) && u.getPasswd().equals(passwd)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Creates a user and adds it to the list of users
     *
     * @param ID        int
     * @param firstName String
     * @param lastName  String
     * @param email     String
     * @param passwd    String
     * @param age       int
     */
    public void addUserService(int ID, String firstName, String lastName, String email, String passwd, int age) throws IOException {
        User user = new User(ID, firstName, lastName, email, passwd, age);
        validator.validate(user);
        try {
            repo.save(user);
            this.notifyObservers(new UserEntityChangeEvent(ChangeEventType.ADD, user));
        } catch (ValidatorException ve) {
            System.out.println(ve.getMessage());
        }
    }

    /**
     * Deletes a user based on ID
     *
     * @param ID int
     */
    public void deleteUserService(int ID) throws IOException {
        for (User u : repo.getAll()) {
            for (Friendship fr : friendships.getAll()) {
                if ((fr.getIdU1() == u.getID() || fr.getIdU2() == u.getID()) && u.getID() == ID) {
                    friendships.delete(fr);
                }
            }
        }
        repo.delete(new User(ID, "a", "b", "a.com", "abcdefgha", 20));
        this.notifyObservers(new UserEntityChangeEvent(ChangeEventType.DELETE, new User(ID, "a", "b", "a.com", "abcdefgha", 20)));
    }

    /**
     * The user with the ID ID becomes friends with the user with the ID ID2
     * @param ID  int, user 1
     * @param ID2 int, user 2
     */
    public void addFriendService(int ID, int ID2) throws IOException {
        User found1 = null;
        User found2 = null;
        for (User u : repo.getAll()) {
            if (u.getID() == ID) {
                found1 = u;
            }
        }

        for (User u : repo.getAll()) {
            if (u.getID() == ID2) {
                found2 = u;
            }
        }

        for (Friendship fr : friendships.getAll()) {
            assert found1 != null;
            assert found2 != null;
            if (fr.getIdU1() == found1.getID() && fr.getIdU2() == found2.getID()) {
                throw new IllegalArgumentException("These users are already friends!\n");
            }
        }

        assert found1 != null;
        assert found2 != null;
        found1.getFriends().add(found2);
        found2.getFriends().add(found1);
        friendships.save(new Friendship(found1.getID(), found2.getID(), LocalDateTime.now()));
    }

    /**
     * Accepts a friendship
     * @param fr Friendship
     */
    public void acceptFriendship(Friendship fr) {
        fr.setStatus(FriendshipStatus.ACCEPTED);
        friendships.update(fr);
    }

    /**
     * The user with the ID ID removes his friendship with the user with the ID ID2
     * @param ID   int, user 1
     * @param ID2, int user 2
     */
    public void deleteFriendService(int ID, int ID2) throws IOException {
        for(Friendship fr: friendships.getAll()){
            if(fr.getIdU1() == ID && fr.getIdU2() == ID2){
                friendships.delete(fr);
                break;
            }
        }
    }

    /**
     * DFS on a copy of the List of users
     * @param copy List of users
     */
    public void DFS(List<User> copy) {
        Stack<User> s = new Stack<>();
        s.push(copy.remove(0));
        while (!s.isEmpty()) {
            User user = s.pop();
            for (User fr : user.getFriends()) {
                if (copy.contains(fr)) {
                    s.push(fr);
                }
            }
            copy.remove(user);
        }
    }

    /**
     * Returns the number of connected components in the network
     * @return int, the number of connected components
     */
    public int connectedCommunities() {
        int connected = 0;
        List<User> copy = new ArrayList<>(repo.getAll());
        while (!copy.isEmpty()) {
            DFS(copy);
            connected++;
        }
        return connected;
    }

    @Override
    public void addObserver(Observer<UserEntityChangeEvent> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<UserEntityChangeEvent> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(UserEntityChangeEvent t) {
        observers.forEach(x -> x.update(t));
    }
}
