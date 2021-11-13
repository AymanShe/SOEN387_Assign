package com.soen387.dataaccess;

import com.mysql.cj.jdbc.CallableStatement;
import com.soen387.controller.Utility;
import com.soen387.model.Choice;
import com.soen387.model.Poll;

import java.sql.*;

public class PollDao {
    public int createPoll(Poll poll) throws SQLException, ClassNotFoundException {

        //assign a poll id
        poll.setPollId(Utility.generatePollId());

        int result = 0;

        String checkIdQuery = "SELECT * FROM poll WHERE poll_id = '?'";
        String pollInsertQuery = "INSERT INTO poll (poll_id, title, question, create_date, create_time, created_by, status) VALUES (?, ?, ?, ?, ?, ?, ?);";
        String choiceInsertQuery = "INSERT INTO choice (poll_id, text, description, choice_number) values (?, ? ,? ,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement insertPollStatement = connection.prepareStatement(pollInsertQuery);
             PreparedStatement checkPollIdStatement = connection.prepareStatement(checkIdQuery);
             PreparedStatement insertChoiceStatement = connection.prepareStatement(choiceInsertQuery)) {

            //check if the Poll Id is already used
            insertPollStatement.setString(1, poll.getPollId());
            ResultSet checkResult = checkPollIdStatement.executeQuery();
            while (checkResult.next()) {//TODO potential infinite loop
                poll.setPollId(Utility.generatePollId());
                insertPollStatement.setString(1, poll.getPollId());
                checkResult = checkPollIdStatement.executeQuery();
            }

            //TODO use transactions

            insertPollStatement.setString(1, poll.getPollId());
            insertPollStatement.setString(2, poll.getName());
            insertPollStatement.setString(3, poll.getQuestion());
            insertPollStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            insertPollStatement.setTime(5, new Time(System.currentTimeMillis()));
            insertPollStatement.setString(6, "default_user");
            insertPollStatement.setString(7, Poll.PollStatus.created.toString());

            System.out.println("New poll query: " + insertPollStatement);

            result = insertPollStatement.executeUpdate();

            for (Choice c : poll.getChoices()) {
                insertChoiceStatement.setString(1, poll.getPollId());
                insertChoiceStatement.setString(2, c.getText());
                if (c.getDescription() == null || c.getDescription().isEmpty()) {
                    insertChoiceStatement.setNull(3, Types.VARCHAR);
                } else {
                    insertChoiceStatement.setString(3, c.getDescription());
                }
                insertChoiceStatement.setInt(4, c.getNumber());
                insertChoiceStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
