package com.soen387.model;

import com.soen387.business.PollException;
import com.soen387.business.PollStateException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

public class Poll implements Serializable {

	public enum PollStatus {
		created, 
		running, 
		released,
		closed;
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
	private Date releaseDate;
	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

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
		releaseDate = null;
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
		if (poll.getReleaseDate() != null){
			releaseDate = new Date(poll.getReleaseDate().getTime());
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
		this.votes = new int[choices.length];
	}

	public void setVotes(int[] votes) {
		this.votes = votes;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
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

	public Date getReleaseDate() { return releaseDate; }

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
			releaseDate = null;
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
		releaseDate = new Date();
	}
	
	public void unrelease() throws PollStateException {
		if (status != PollStatus.released) {
			throw new PollStateException("Cannot unrelease the Poll while it is not in the released state.");
		}
		
		status = PollStatus.running;
		releaseDate = null;
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

	public String toJson() {
		JSONObject pollJson = new JSONObject();
		pollJson.put("name", name);
		pollJson.put("question", question);

		JSONArray choicesJson = new JSONArray();
		for (int i = 0; i < votes.length; i++) {
			JSONObject choiceJson = new JSONObject();
			choiceJson.put("votes", votes[i]);
			choiceJson.put("choice", choices[i].getText());
			choiceJson.put("description", choices[i].getDescription());

			choicesJson.add(choiceJson);
		}

		pollJson.put("choices", choicesJson);

		return pollJson.toString();
	}

	public String toXML() {
		Document xml;
		Element root = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			xml = builder.newDocument();
			Element rootElement = xml.createElement("poll");

			Element nameElement = xml.createElement("name");
			nameElement.appendChild(xml.createTextNode(name));
			rootElement.appendChild(nameElement);

			Element questionElement = xml.createElement("question");
			questionElement.appendChild(xml.createTextNode(question));
			rootElement.appendChild(questionElement);

			Element choicesElement = xml.createElement("choices");

			for (int i = 0; i < votes.length; i++) {
				Element choiceElement = xml.createElement("choice");

				Element voteElement = xml.createElement("votes");
				voteElement.appendChild(xml.createTextNode(Integer.toString(votes[i])));
				choiceElement.appendChild(voteElement);

				Element choiceNameElement = xml.createElement("choiceName");
				choiceNameElement.appendChild(xml.createTextNode(choices[i].getText()));
				choiceElement.appendChild(choiceNameElement);

				Element choiceDescElement = xml.createElement("choiceDesc");
				choiceDescElement.appendChild(xml.createTextNode(choices[i].getDescription()));
				choiceElement.appendChild(choiceDescElement);

				choicesElement.appendChild(choiceElement);

			}

			rootElement.appendChild(choicesElement);

			xml.appendChild(rootElement);

			try {
				StringWriter sw = new StringWriter();
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

				transformer.transform(new DOMSource(xml), new StreamResult(sw));
				return sw.toString();
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return "";
	}
}
