package com.soen387.business;

import com.soen387.dataaccess.PollDao;
import com.soen387.model.Choice;
import com.soen387.model.Poll;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

public class PollManager implements Serializable {

	private PollDao pollDao = new PollDao();

	public  void createPoll(Poll poll) throws SQLException, ClassNotFoundException {
		try {
			pollDao.createPoll(poll);
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		}
	}

	public String downloadPollDetails(PrintWriter output, StringBuilder fileName, Poll poll, String format) throws PollException {
        if (poll == null) {
            throw new PollNotFoundException("Cannot download the poll results when none exists.");
        }

        if (poll.getStatus() != Poll.PollStatus.released && poll.getStatus() != Poll.PollStatus.closed) {
            throw new PollStateException("Cannot download the poll results while the Poll is not in the released or closed state.");
        }

		String content = "";
		if (format.equalsIgnoreCase("json")) content = poll.toJson();
		else if (format.equalsIgnoreCase("xml")) content = poll.toXML();
		else {
			format = "txt";
			content = poll.toString();
		}

		String name = poll.getName() + "-" + poll.getReleaseDate().toString() + "." + format;
		fileName.delete(0, fileName.length());
		fileName.append(name);

		output.print(content);

		return name;
	}

	public Poll getPoll(String pollId) {
		Poll poll = pollDao.getPoll(pollId);
		if (poll.getPollId() == null){
			return null;
		}
		return poll;
	}

	public void releasePoll(Poll poll) {
		//TODO check if action is allowed
		poll.setStatus(Poll.PollStatus.released);
		poll.setReleaseDate(new Date());
		pollDao.updatePoll(poll);
	}

	public void unreleasePoll(Poll poll) {
		//TODO check if action is allowed
		poll.setStatus(Poll.PollStatus.running);
		poll.setReleaseDate(null);
		pollDao.updatePoll(poll);
	}

	public void closePoll(Poll poll) {
		//TODO check if action is allowed
		poll.setStatus(Poll.PollStatus.closed);
		pollDao.updatePoll(poll);
	}

	public void deletePoll(Poll poll) {
		//TODO check if action is allowed
		pollDao.deletePoll(poll);
	}

	public void runPoll(Poll poll) {
		//TODO check if action is allowed
		poll.setStatus(Poll.PollStatus.running);
		pollDao.updatePoll(poll);
	}

    public List<Poll> getPollsByUserName(String userName) {
		return pollDao.getPollByUserName(userName);
    }

    public List<String> getAllowedActions(Poll poll){
		List<String> allowedActions = new ArrayList<>();
		//based on status
		switch (poll.getStatus()){
			case created:
				allowedActions.add("Run");
				allowedActions.add("Edit");
				break;
			case running:
				allowedActions.add("Release");
				allowedActions.add("Edit");
				break;
			case released:
				allowedActions.add("Unrelease");
				allowedActions.add("Close");
				break;
			case closed:
				break;
			default:
				break;
		}

		//based on votes
		if(poll.getVotes() == null || poll.getVotes().length == 0){
			allowedActions.add("Delete");
		}

		return allowedActions;

	}

	public void editPoll(Poll poll) throws PollException {
		try {
			pollDao.editPoll(poll);
		} catch (SQLException e) {
			//TODO handle all sql exceptions like this by passing them to a poll exception
			throw new PollException(e.getMessage());
		}
	}

	public int getChoiceNumber(String pinId, String pollId) {
		return pollDao.getChoiceNumber(pinId, pollId);
	}

	public int createVote(String pollId, String choiceNumber) throws PollException  {
		try {
			int pinId = pollDao.createVote(pollId, choiceNumber);
			if (pinId == 0){
				throw new PollException("Something went wrong. You vote wasn't registered. Please try again later.");
			}
			return pinId;
		} catch (SQLException e) {
			throw new PollException(e.getMessage());
		}
	}

	public void updateVote(String pinId, String pollId, String choiceNumber) throws PollException  {
		try {
			pollDao.updateVote(Integer.parseInt(pinId), pollId, Integer.parseInt(choiceNumber));
		} catch (SQLException e) {
			throw new PollException(e.getMessage());
		}
	}
}
