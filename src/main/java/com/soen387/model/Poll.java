package com.soen387.model;

import com.soen387.business.PollException;
import com.soen387.business.PollStateException;

import java.util.Date;
import java.util.Hashtable;

public class Poll {

	public enum PollStatus {
		created, 
		running, 
		released;
	}
	private String pollId;

	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	private String name;
	private String question;
	private PollStatus status;
	private Choice[] choices;
	private int[] votes;
	private Date releaseTime;

	public Poll() {
	}

	public Poll(String _name, String _question, Choice[] _choices) throws PollException {
		if (_choices.length < 2) {
			throw new PollException("Cannot create a Poll with less than 2 choices.");
		}
		name = _name;
		question = _question;
		status = PollStatus.created;
		choices = _choices;
		votes = new int[choices.length];
		releaseTime = null;
	}

	// Deep copy
	public Poll(Poll poll) {
		name = poll.getName();
		question = poll.getQuestion();
		status = poll.getStatus();
		choices = new Choice[poll.getChoices().length];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = new Choice(poll.getChoices()[i]);
		}
		votes = poll.getVotes().clone();
		if (poll.getReleaseTime() != null){
			releaseTime = new Date(poll.getReleaseTime().getTime());
		}
	}
	
	public void addVote(int choiceIndex) {
		votes[choiceIndex]++;
	}
	
	public void changeVote(int oldChoiceIndex, int newChoiceIndex) {
		if (votes[oldChoiceIndex] == 0) {
			return;
		}
		votes[oldChoiceIndex]--;
		votes[newChoiceIndex]++;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setStatus(PollStatus status) {
		this.status = status;
	}

	public void setChoices(Choice[] choices) {
		this.choices = choices;
	}

	public void setVotes(int[] votes) {
		this.votes = votes;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getName() {
		return name;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public PollStatus getStatus() {
		return status;
	}
	
	public Choice[] getChoices() {
		return choices;
	}
	
	public int[] getVotes() {
		return votes;
	}

	public Date getReleaseTime() { return releaseTime; }

	public void update(String _name, String _question, Choice[] _choices) throws PollException {
		if (status != PollStatus.running) {
			throw new PollStateException("Cannot update the Poll while it is not in the running state.");
		} else if (_choices.length < 2) {
			throw new PollException("Cannot update the Poll to have less than 2 questions.");
		}
		
		name = _name;
		question = _question;
		status = PollStatus.created;
		choices = _choices;
		votes = new int[choices.length];
	}
	
	public void clear() throws PollStateException {
		if (status == PollStatus.created) {
			throw new PollStateException("Cannot clear the Poll while it is in the created state.");
		}
		
		for (int i = 0; i < votes.length; i++) {
			votes[i] = 0;
		}
		
		if (status == PollStatus.released) {
			status = PollStatus.created;
			releaseTime = null;
		}
	}
	
	public void run() throws PollStateException {
		if (status != PollStatus.created) {
			throw new PollStateException("Cannot run the Poll while it is not in the created state.");
		}
		
		status = PollStatus.running;
	}

	public void release() throws PollStateException {
		if (status != PollStatus.running) {
			throw new PollStateException("Cannot release the Poll while it is not in the running state.");
		}

		status = PollStatus.released;
		releaseTime = new Date();
	}
	
	public void unrelease() throws PollStateException {
		if (status != PollStatus.released) {
			throw new PollStateException("Cannot unrelease the Poll while it is not in the released state.");
		}
		
		status = PollStatus.running;
		releaseTime = null;
	}
	
	public Hashtable<Integer, Integer> getResults() throws PollStateException {
		if (status != PollStatus.released) {
			throw new PollStateException("Cannot view the Poll results while it is not in the released state.");
		}
		
		Hashtable<Integer, Integer> results = new Hashtable<Integer, Integer>();
		for (int i = 0; i < votes.length; i++) {
			results.put(i, votes[i]);
		}
		
		return results;
	}



	public String toString() {
		String text = name;
		text += "\n\n" + question + "\n\n";
		int totalVotes = 0;
		for (int i = 0; i < votes.length; i++) {
			totalVotes += votes[i];
		}

		for (int i = 0; i < votes.length; i++) {
			if (totalVotes > 0) {
				text += votes[i] + " votes (" + (float)votes[i]*100f/(float)totalVotes + "%): ";
			} else {
				text += "0 votes: ";
			}
			text += choices[i].getText() + "\n";
		}

		return text;
	}
}