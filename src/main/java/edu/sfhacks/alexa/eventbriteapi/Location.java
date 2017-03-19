package edu.sfhacks.alexa.eventbriteapi;

import java.util.HashMap;
import java.util.Map;

public class Location {
	
	private String latitude;
	private String within;
	private String longitude;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getLatitude() {
	return latitude;
	}

	public void setLatitude(String latitude) {
	this.latitude = latitude;
	}

	public String getWithin() {
	return within;
	}

	public void setWithin(String within) {
	this.within = within;
	}

	public String getLongitude() {
	return longitude;
	}

	public void setLongitude(String longitude) {
	this.longitude = longitude;
	}

	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
