package com.soen387.dataaccess;

import com.soen387.controller.Utility;
import com.soen387.model.Choice;
import com.soen387.model.Poll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PollDao {
    public int createPoll(Poll poll) throws SQLException, ClassNotFoundException {
        //TODO don't pass business objects rather pass attributes

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
        //TODO recomended to make all query use named parameter
        String getChoicesQuery = "SELECT * FROM choice WHERE poll_id = ?";
        String getVotesQuery = "SELECT * FROM vote WHERE poll_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement getPollStatement = connection.prepareStatement(getPollQuery);
             PreparedStatement getChoicesStatement = connection.prepareStatement(getChoicesQuery);
             PreparedStatement getVotesStatement = connection.prepareStatement(getVotesQuery)) {

            getPollStatement.setString(1, pollId);

            ResultSet pollResult = getPollStatement.executeQuery();
            if (pollResult.next()) {
                poll.setPollId(pollResult.getString("poll_id"));
                poll.setName(pollResult.getString("title"));
                poll.setQuestion(pollResult.getString("question"));
                poll.setStatus(Poll.PollStatus.valueOf(pollResult.getString("status")));
                poll.setReleaseDate(pollResult.getTimestamp("release_timestamp"));
                poll.setCreatedBy(pollResult.getString("created_by"));
            }

            getChoicesStatement.setString(1, pollId);
            ResultSet choiceResult = getChoicesStatement.executeQuery();
            List<Choice> choicesAsList = new ArrayList<>();
            while (choiceResult.next()) {
                Choice choice = new Choice();
                choice.setText(choiceResult.getString("text"));
                choice.setDescription(choiceResult.getString("description"));
                choice.setNumber(choiceResult.getInt("choice_number"));
                choicesAsList.add(choice);
            }
            Choice[] choices = choicesAsList.toArray(new Choice[choicesAsList.size()]);
            poll.setChoices(choices);

            getVotesStatement.setString(1, pollId);
            ResultSet voteResult = getVotesStatement.executeQuery();
            List<Integer> votesAsList = new ArrayList();
            while (voteResult.next()) {
                int choiceNumber = voteResult.getInt("choice_number");
                votesAsList.add(choiceNumber);
            }
            int[] votes = votesAsList.stream().mapToInt(i->i).toArray();
            poll.setVotes(votes);

        } catch (SQLException e) {
            e.printStackTrace();
        }

         return poll;
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
        String deleteChoiceQuery = "DELETE FROM choice WHERE poll_id = ?";
        String deletePollQuery = "DELETE FROM poll WHERE poll_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement deletePollStatement = connection.prepareStatement(deletePollQuery);
             PreparedStatement deleteChoiceStatement = connection.prepareStatement(deleteChoiceQuery)) {

            //TODO use transactions
            deleteChoiceStatement.setString(1, poll.getPollId());
            int choiceRowsEffected = deleteChoiceStatement.executeUpdate();

            deletePollStatement.setString(1, poll.getPollId());
            int pollRowsEffected = deletePollStatement.executeUpdate();

            //TODO handle when 0 rows affected

            //TODO deleteVotes when deletePoll

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Poll> getPollByUserName(String userName) {
        List<Poll> polls = new ArrayList<>();

        String getPollQuery = "SELECT * FROM poll WHERE created_by = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement getPollStatement = connection.prepareStatement(getPollQuery)) {

            getPollStatement.setString(1, userName);

            ResultSet result = getPollStatement.executeQuery();
            while (result.next()) {
                Poll poll = new Poll();
                poll.setPollId(result.getString("poll_id"));
                poll.setName(result.getString("title"));
                poll.setQuestion(result.getString("question"));
                poll.setStatus(Poll.PollStatus.valueOf(result.getString("status")));
                poll.setReleaseDate(result.getTimestamp("release_timestamp"));
                polls.add(poll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return polls;
    }

    public void editPoll(Poll poll) throws SQLException {
        //update poll title and question

        String updatePollQuery = "UPDATE poll set title = ?, question = ?, status = ? WHERE poll_id = ?";
        String deleteChoicesQuery = "DELETE FROM choice WHERE poll_id = ?";
        String deleteVotesQuery = "DELETE FROM vote WHERE poll_id = ?";
        String insertChoiceQuery = "INSERT INTO choice (poll_id, text, description, choice_number) values (?, ? ,? ,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement updatePollStatement = connection.prepareStatement(updatePollQuery);
             PreparedStatement deleteChoicesStatement = connection.prepareStatement(deleteChoicesQuery);
             PreparedStatement insertChoiceStatement = connection.prepareStatement(insertChoiceQuery);
             PreparedStatement deleteVotesStatement = connection.prepareStatement(deleteVotesQuery)) {

            updatePollStatement.setString(1, poll.getName());
            updatePollStatement.setString(2, poll.getQuestion());
            updatePollStatement.setString(3, Poll.PollStatus.created.toString());
            updatePollStatement.setString(4, poll.getPollId());

            int updatePollResult = updatePollStatement.executeUpdate();

            deleteChoicesStatement.setString(1, poll.getPollId());
            int deleteChoiceResult = deleteChoicesStatement.executeUpdate();

            deleteVotesStatement.setString(1, poll.getPollId());
            int deleteVoteResult = deleteVotesStatement.executeUpdate();

            for (Choice c : poll.getChoices()) {
                insertChoiceStatement.setString(1, poll.getPollId());
                insertChoiceStatement.setString(2, c.getText());
                if (c.getDescription() == null || c.getDescription().isEmpty()) {
                    insertChoiceStatement.setNull(3, Types.VARCHAR);
                } else {
                    insertChoiceStatement.setString(3, c.getDescription());
                }
                insertChoiceStatement.setInt(4, c.getNumber());
                System.out.println(insertChoiceStatement);
                insertChoiceStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    //TODO: Fix to properly retrieve vote for updateVote
    public int getChoiceNumber(String pinId, String pollId) {
        String getVoteQuery = "SELECT choice_number FROM vote WHERE pin_id = ? AND poll_id = ?";
        int choiceNumber = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement getVoteStatement = connection.prepareStatement(getVoteQuery)) {

            getVoteStatement.setString(1, pinId);
            getVoteStatement.setString(2, pollId);

            ResultSet voteResult = getVoteStatement.executeQuery();

            if (voteResult.next()){
                choiceNumber = voteResult.getInt("choice_number");

                //TODO: May need timestamp later
                //Timestamp timestamp = voteResult.getTimestamp("release_timestamp");
            }

            return choiceNumber;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return choiceNumber;
    }

    public int createVote(String pollId, String choiceNumber) throws SQLException  {
        String createVoteQuery = "INSERT INTO vote (pin_id, poll_id, choice_number, create_timestamp) values(?, ?, ?, ?)";
        String checkVoteQuery = "SELECT * FROM vote WHERE poll_id = ? AND pin_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement createVoteStatement = connection.prepareStatement(createVoteQuery);
             PreparedStatement checkVoteStatement = connection.prepareStatement(checkVoteQuery)) {

            int pinId = Utility.generatePinId();
            //check if the Poll Id is already used
            checkVoteStatement.setString(1, pollId);
            checkVoteStatement.setInt(2, pinId);
            ResultSet checkResult = checkVoteStatement.executeQuery();
            while (checkResult.next()) {//TODO potential infinite loop
                pinId = Utility.generatePinId();
                checkVoteStatement.setInt(2, pinId);
                checkResult = checkVoteStatement.executeQuery();
            }

            createVoteStatement.setInt(1, pinId);
            createVoteStatement.setString(2, pollId);
            createVoteStatement.setString(3, choiceNumber);
            createVoteStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

            int updatePollResult = createVoteStatement.executeUpdate(); //TODO use returned pinId

            if (updatePollResult != 0) {
                return pinId;
            }
            else {
                return 0;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void updateVote(int pinId, String pollId, int choiceNumber) throws SQLException  {
        //TODO: need to pre-validate if pin_id & poll_id exist (will do when load vote results to update)
        String updateVoteQuery = "UPDATE vote SET choice_number = ?, create_timestamp = ?  WHERE pin_id = ? AND poll_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement updateVoteStatement = connection.prepareStatement(updateVoteQuery)) {

            updateVoteStatement.setInt(1, choiceNumber);
            updateVoteStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            updateVoteStatement.setInt(3, pinId);
            updateVoteStatement.setString(4, pollId);

            int updatePollResult = updateVoteStatement.executeUpdate(); //TODO use returned pinId (this int returns # rows affected)
        } catch (SQLException e) {
            throw e;
        }
    }
}
