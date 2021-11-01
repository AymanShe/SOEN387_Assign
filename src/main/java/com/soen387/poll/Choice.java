package com.soen387.poll;

public class Choice {
	
	private String text;
	private String description;
	
	public Choice(String _text, String _description) {
		text = _text;
		description = _description;
	}

	public Choice(Choice choice) {
		text = choice.getText();
		description = choice.getDescription();
	}
	
	public String getText() {
		return text;
	}
	
	public String getDescription() {
		return description;
	}
}
