package com.example.lab6_socialnetwork_gui.repo.database;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.User;
import com.example.lab6_socialnetwork_gui.repo.Repository;
import javafx.beans.property.ReadOnlyStringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDBRepo implements Repository<Long, User> {
    private final JDBCUtils jdbcUtils = new JDBCUtils();

//    public UserDBRepo() {
//        this.addFriends();
//    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String paswd = resultSet.getString("passwd");
                int age = resultSet.getInt("age");

                User user = new User(ID, firstName, lastName, email, paswd, age);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public boolean delete(User entity) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, entity.getID());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User update(User entity) {
        String query = "UPDATE users SET firstName = ?, lastName = ?, email = ?, passwd = ?, age = ? WHERE id = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPasswd());
            statement.setInt(5, entity.getAge());
            statement.setInt(6, entity.getID());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public User save(User entity) {
        String query = "INSERT INTO users(ID, firstName, lastName, email, passwd, age) VALUES(?,?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, entity.getID());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPasswd());
            statement.setInt(6, entity.getAge());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public void addFriends() {
        for (User u : this.getAll()) {
            for (Friendship fr : this.findFriends()) {
                if (u.getID() == fr.getIdU1()) {
                    User friend = findOne((int) fr.getIdU2());
                    u.getFriends().add(friend);
                } else if (u.getID() == fr.getIdU2()) {
                    User friend = findOne((int) fr.getIdU1());
                    u.getFriends().add(friend);
                }
            }
        }
    }

    public User findOne(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int ID = resultSet.getInt("id");
            return new User(ID, resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("email"), resultSet.getString("passwd"), resultSet.getInt("age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean findUser(String email, String passwd){
        String query = "SELECT EXISTS (SELECT 1 FROM users WHERE email = ? AND passwd = ?)";
        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, email);
            statement.setString(2, passwd);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next() && Objects.equals(resultSet.getString(1), "t")){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<Friendship> findFriends() {
        List<Friendship> friendList = new ArrayList<>();

        String query = "SELECT * FROM friendships";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int u1ID = resultSet.getInt("idu1");
                int u2ID = resultSet.getInt("idu2");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                Friendship friendship = new Friendship(u1ID, u2ID, date);
                friendList.add(friendship);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendList;
    }


    @Override
    public int size() {
        return getAll().size();
    }
}
