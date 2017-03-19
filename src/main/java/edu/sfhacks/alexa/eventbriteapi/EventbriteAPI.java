package edu.sfhacks.alexa.eventbriteapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class EventbriteAPI {

	private Pagination pagination;
	private List<Event> events = null;
	private Location location;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Pagination getPagination() {
	return pagination;
	}

	public void setPagination(Pagination pagination) {
	this.pagination = pagination;
	}

	public List<Event> getEvents() {
	return events;
	}

	public void setEvents(List<Event> events) {
	this.events = events;
	}

	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}

	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

/*	private static final String uri = "https://www.eventbriteapi.com/v3/events/search?categories=103,113,110&location.latitude=37.726585&location.longitude=-122.482036&location.within=10mi&token=AJYECS6IZV3QFAKTILC6";
	
	//private static final String CLIENT = "";
	
	public String getData() throws IOException{

		URL obj = new URL(uri);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type","application/json");

		// Send post request
		con.setDoOutput(true);
		//DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.flush();
		//wr.close();

		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
		return response.toString();
	}
	*/
	
	/*public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		EventbriteAPI api = new EventbriteAPI();
		api = gson.fromJson(new Data().data, EventbriteAPI.class);
		for(Event eve: api.getEvents()){
			System.out.println(eve.getName().getText());
		}
	
	}*/
}