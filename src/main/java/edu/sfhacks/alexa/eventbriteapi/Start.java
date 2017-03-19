package edu.sfhacks.alexa.eventbriteapi;

public class Start {

	private String timezone;
	private String local;
	private String utc;
	//private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUtc() {
		return utc;
	}

	public void setUtc(String utc) {
		this.utc = utc;
	}

}
