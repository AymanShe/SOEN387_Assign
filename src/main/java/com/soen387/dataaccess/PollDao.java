package com.soen387.dataaccess;

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
        String pollInsertQuery = "INSERT INTO poll (poll_id, title, question, create_timestamp, created_by, status) VALUES (?, ?, ?, ?, ?, ?);";
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
            insertPollStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            insertPollStatement.setString(5, poll.getCreatedBy());
            insertPollStatement.setString(6, Poll.PollStatus.created.toString());

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

    public Poll getPoll(String pollId) {
        Poll poll = new Poll();

        String getPollQuery = "SELECT * FROM poll WHERE poll_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement getPollStatement = connection.prepareStatement(getPollQuery)) {

            getPollStatement.setString(1, pollId);

            ResultSet result = getPollStatement.executeQuery();
            if (result.next()) {
                poll.setPollId(result.getString("poll_id"));
                poll.setName(result.getString("title"));
                poll.setQuestion(result.getString("question"));
                poll.setStatus(Poll.PollStatus.valueOf(result.getString("status")));
                poll.setReleaseDate(result.getTimestamp("release_timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return poll == null ? null : poll;
        }
    }

    public void updatePoll(Poll poll) {
        String updateQuery = "UPDATE poll SET title = ?, question = ?, status = ?, release_timestamp = ? WHERE poll_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement updatePollStatement = connection.prepareStatement(updateQuery)) {

            updatePollStatement.setString(1, poll.getName());
            updatePollStatement.setString(2, poll.getQuestion());
            updatePollStatement.setString(3, poll.getStatus().toString());
            if (poll.getReleaseDate() == null) {
                updatePollStatement.setNull(4, Types.DATE);
            } else {
                updatePollStatement.setTimestamp(4, new Timestamp(poll.getReleaseDate().getTime()));
            }
            updatePollStatement.setString(5, poll.getPollId());

            System.out.println("Update poll query: " + updatePollStatement);

            int rowsEffected = updatePollStatement.executeUpdate();

            //TODO handle when 0 rows effected

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePoll(Poll poll) {
        String updateQuery = "DELETE FROM poll WHERE poll_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement deletePollStatement = connection.prepareStatement(updateQuery)) {


            deletePollStatement.setString(1, poll.getPollId());

            System.out.println("Delete poll query: " + deletePollStatement);
            int rowsEffected = deletePollStatement.executeUpdate();

            //TODO handle when 0 rows effected

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
