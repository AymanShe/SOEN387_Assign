package com.soen387.business;

import com.soen387.dataaccess.PollDao;
import com.soen387.model.Choice;
import com.soen387.model.Poll;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

public class PollManager implements Serializable {

	private Poll poll;
	private Hashtable<String, Integer> voters;
	private PollDao pollDao = new PollDao();
	
	public PollManager() {
		poll = null;
		voters = new Hashtable<String, Integer>();
	}
	
	public void createPoll(String name, String question, Choice[] choices) throws PollException {
		if (poll != null) {
			throw new PollStateException("Cannot create a new Poll while one already exists.");
		}
		
		try {
			poll = new Poll(name, question, choices);
		} catch (PollException e) {
			throw e;
		}
	}

	public  void createPoll(Poll poll) throws SQLException, ClassNotFoundException {
		try {
			pollDao.createPoll(poll);
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		}
	}
	
	public void updatePoll(String name, String question, Choice[] choices) throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot update the Poll when none exists.");
		}
		
		try {
			poll.update(name, question, choices);
		} catch (PollStateException e) {
			throw e;
		}
	}
	
	public void clearPoll() throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot update the Poll when none exists.");
		}
		
		try {
			poll.clear();
		} catch (PollStateException e) {
			throw e;
		}
	}
	
	public void closePoll() throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot close the Poll when none exists.");
		}
		
		if (poll.getStatus() != Poll.PollStatus.released) {
			throw new PollStateException("Cannot close the Poll while it is not in the released status.");
		}
		
		poll = null;
	}
	
	public void runPoll() throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot run the Poll when none exists.");
		}
		
		try {
			poll.run();
		} catch (PollStateException e) {
			throw e;
		}
	}

	public void releasePoll() throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot release the Poll when none exists.");
		}

		try {
			poll.release();
		} catch (PollStateException e) {
			throw e;
		}
	}
	
	public void unreleasePoll() throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot unrelease the Poll when none exists.");
		}
		
		try {
			poll.unrelease();
		} catch (PollStateException e) {
			throw e;
		}
	}
	
	public void vote(String participant, int choice) throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot vote when no Poll exists.");
		}
		
		if (poll.getStatus() != Poll.PollStatus.running) {
			throw new PollStateException("Cannot vote while the Poll is not in the running state.");
		}
		
		if (voters.containsKey(participant)) {
			poll.changeVote(voters.get(participant).intValue(), choice);
		} else {
			poll.addVote(choice);
		}
		
		voters.put(participant, choice);
	}
	
	public Hashtable<Integer, Integer> getPollResult() throws PollException {
		if (poll == null) {
			throw new PollNotFoundException("Cannot view the poll results when none exists.");
		}
		
		try {
			return poll.getResults();
		} catch (PollStateException e) {
			throw e;
		}
	}

	public Poll getPoll() {
		return new Poll(poll);
	}

	public String[] getAvailableActions() {
		String actions[];
		if (poll == null) {
			actions = new String[1];
			actions[0] = "Create";
		}
		else if (poll.getStatus() == Poll.PollStatus.created) {
			actions = new String[2];
			actions[0] = "Run";
			actions[1] = "Update";
		}
		else if (poll.getStatus() == Poll.PollStatus.running) {
			actions = new String[3];
			actions[0] = "Clear";
			actions[1] = "Update";
			actions[2] = "Release";
		}
		else {
			actions = new String[3];
			actions[0] = "Clear";
			actions[1] = "Unrelease";
			actions[2] = "Close";
		}
		return actions;
	}

	public String downloadPollDetails(PrintWriter output, StringBuilder fileName, Poll poll, String format) throws PollException {
        if (poll == null) {
            throw new PollNotFoundException("Cannot download the poll results when none exists.");
        }

        if (poll.getStatus() != Poll.PollStatus.released) {
            throw new PollStateException("Cannot download the poll results while the Poll is not in the released state.");
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
		return pollDao.getPoll(pollId);
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

	public void createVote(String pollId, String choiceNumber) throws PollException  {
		try {
			pollDao.createVote(pollId, choiceNumber);
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
