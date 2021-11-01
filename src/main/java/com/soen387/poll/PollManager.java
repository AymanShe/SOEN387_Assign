package com.soen387.poll;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Hashtable;

public class PollManager implements Serializable {

	private Poll poll;
	private Hashtable<String, Integer> voters;
	
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

	public String downloadPollDetails(PrintWriter output, StringBuilder fileName) throws PollException {
        if (poll == null) {
            throw new PollNotFoundException("Cannot download the poll results when none exists.");
        }

        if (poll.getStatus() != Poll.PollStatus.released) {
            throw new PollStateException("Cannot download the poll results while the Poll is not in the released state.");
        }

		String name = poll.getName() + "-" + poll.getReleaseTime().toString() + ".txt";
		fileName.delete(0, fileName.length());
		fileName.append(name);

		output.print(poll.toString());

		return name;
	}
}
