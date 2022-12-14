package com.example.lab6_socialnetwork_gui.repo.database;

import com.example.lab6_socialnetwork_gui.domain.Friendship;
import com.example.lab6_socialnetwork_gui.domain.FriendshipStatus;
import com.example.lab6_socialnetwork_gui.repo.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDBRepo implements Repository<Long, Friendship> {
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public List<Friendship> getAll() {
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
                FriendshipStatus status = FriendshipStatus.valueOf(resultSet.getString("status"));
                Friendship friendship = new Friendship(u1ID, u2ID, date);
                if (status == FriendshipStatus.ACCEPTED) {
                    friendship.setStatus(FriendshipStatus.ACCEPTED);
                } else friendship.setStatus(FriendshipStatus.PENDING);
                friendList.add(friendship);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendList;
    }

    @Override
    public boolean delete(Friendship entity) {
        String query = "DELETE FROM friendships WHERE idu1 = ? and idu2 = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, (int) entity.getIdU1());
            statement.setInt(2, (int) entity.getIdU2());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Friendship update(Friendship entity) {
        String query = "UPDATE friendships SET date = ?, status = ? WHERE idu1= ? and idu2 = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
            statement.setString(2, String.valueOf(entity.getStatus()));
            statement.setInt(3, (int) entity.getIdU1());
            statement.setInt(4, (int) entity.getIdU2());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Friendship save(Friendship entity) {
        String query = "INSERT INTO friendships(idu1, idu2, date, status) VALUES(?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, (int) entity.getIdU1());
            statement.setInt(2, (int) entity.getIdU2());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            statement.setString(4, String.valueOf(entity.getStatus()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public int size() {
        return getAll().size();
    }
}
