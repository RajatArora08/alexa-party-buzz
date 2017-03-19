package edu.sfhacks.alexa.eventbriteapi;

import java.util.HashMap;
import java.util.Map;

public class Description {
	private String text;
	private String html;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getText() {
	return text;
	}

	public void setText(String text) {
	this.text = text;
	}

	public String getHtml() {
	return html;
	}

	public void setHtml(String html) {
	this.html = html;
	}

	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
