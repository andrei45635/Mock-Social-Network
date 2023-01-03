package com.example.lab6_socialnetwork_gui.repo.database;

import com.example.lab6_socialnetwork_gui.domain.Message;
import com.example.lab6_socialnetwork_gui.repo.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDBRepo implements Repository<Long, Message> {
    private final JDBCUtils utils = new JDBCUtils();

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages";
        try (Connection connection = utils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int receiverID = resultSet.getInt("receiverID");
                int senderID = resultSet.getInt("senderID");
                String message = resultSet.getString("message");
                Message msg = new Message(receiverID, senderID, message);
                messages.add(msg);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messages;
    }

    @Override
    public boolean delete(Message entity) throws IOException {
        String query = "DELETE FROM messages WHERE senderID = ? AND receiverID = ?";
        try (Connection connection = utils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.getSenderID());
            statement.setInt(2, entity.getReceiverID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Message update(Message entity) throws IOException {
        return null;
    }

    @Override
    public Message save(Message entity) throws IOException {
        String query = "INSERT INTO messages(senderID, receiverID, message) VALUES (?, ?, ?)";
        try(Connection connection = utils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.getSenderID());
            statement.setInt(2, entity.getReceiverID());
            statement.setString(3, entity.getMessage());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public int size() {
        return this.getAll().size();
    }
}
