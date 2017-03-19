package edu.sfhacks.alexa.eventbriteapi;


public class Event {

	private Name name;
	private Description description;
	private Start start;

	public Name getName() {
	return name;
	}

	public void setName(Name name) {
	this.name = name;
	}

	public Description getDescription() {
	return description;
	}

	public void setDescription(Description description) {
	this.description = description;
	}

	public Start getStart() {
		return start;
	}

	public void setStart(Start start) {
		this.start = start;
	}

}
