package com.soen387.dao;

import com.soen387.poll.Poll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PollDao {
    public int createPoll(Poll poll) throws SQLException, ClassNotFoundException {

        int result = 0;

        try (Connection connection = DBConnection.getConnection()) {

            String query = "INSERT INTO poll (id, title, question) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, poll.getName());
            preparedStatement.setString(3, poll.getQuestion());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
