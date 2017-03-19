package com.alexa.partyhouse;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.google.gson.Gson;

import edu.sfhacks.alexa.eventbriteapi.Data;
import edu.sfhacks.alexa.eventbriteapi.Event;
import edu.sfhacks.alexa.eventbriteapi.EventbriteAPI;


public class PartyHouseSpeechlet implements Speechlet{

	private static final Logger log = LoggerFactory.getLogger(PartyHouseSpeechlet.class);
	
	private static final String SLOT_TICKETS_COUNT = "TicketsCount";
	
	private List<Event> event_list = new ArrayList<>();

	private static int SESSION_EVENT_INDEX = -1;


	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {

		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());
		
		Gson gson = new Gson();
		EventbriteAPI api = new EventbriteAPI();
		api = gson.fromJson(new Data().data, EventbriteAPI.class);
		
		event_list = api.getEvents();
		
		log.info("List size= "+event_list.size());

		String speechOutput = "Welcome to party buzz! You can ask what's happening around ?";

		// Reprompt speech will be triggered if the user doesn't respond.
		String repromptText = "Ask me for parties happening around";

		SimpleCard card = new SimpleCard();
		card.setTitle("Party Buzz");
		card.setContent(speechOutput);

		session.setAttribute("SESSION_EVENT_INDEX", SESSION_EVENT_INDEX);
		
		return newAskResponse(speechOutput, false, repromptText, false);

	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {

		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());

	}

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {

		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());

	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {

		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("GetEventsIntent".equals(intentName)) {
			return getIntentResponse(session, false);
		} else if("NextEventIntent".equals(intentName)) {
			return getIntentResponse(session, false);
		} else if("PreviousEventIntent".equals(intentName)) {
			return getIntentResponse(session, true);
		}  else if("DetailsEventIntent".equals(intentName)) {
			return getDetailsIntentResponse(session);
		} else if("BookEventIntent".equals(intentName)) {
			return getBookIntentResponse(request.getIntent(), session);
		}
		
		else if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else {
			throw new SpeechletException("Invalid Intent");
		}

	}


	private SpeechletResponse getBookIntentResponse(Intent intent, Session session) {
		// TODO Auto-generated method stub
		int value = (int) session.getAttribute("PRICE");
		
		int noOfTickets = Integer.parseInt(intent.getSlot(SLOT_TICKETS_COUNT).getValue());
		
		SimpleCard card = new SimpleCard();
		card.setContent("Event booked. Total Bill: "+ value*noOfTickets +" USD");
		card.setTitle("Party Buzz");
		
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Awesome! I bought the tickets for you. Total bill is "+ value*noOfTickets +" USD. Enjoy the party!");
		
		return SpeechletResponse.newTellResponse(speech, card); 
		
	}

	private SpeechletResponse getDetailsIntentResponse(Session session) {
		// TODO Auto-generated method stub
		
		String repromptText = "Please respond";
		
		Event event = event_list.get((int) session.getAttribute("SESSION_EVENT_INDEX"));
		
//		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Date d = new Date();
		try {
			d = sdf.parse(event.getStart().getLocal());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random rand = new Random(); 
		int value = rand.nextInt(50); 
		
		String date = "<say-as interpret-as=\"date\" format=\"dm\">" + (new SimpleDateFormat("dd-MM").format(d)) + "</say-as>";
	
		String speechOutput = "<speak>This event is on" + date + " and the cost is "+ value +" USD </speak>";
		
		session.setAttribute("PRICE", value);
		
		log.info("***** "+speechOutput);
		
		return newAskResponse(speechOutput, true, repromptText, false);
	}

	public SpeechletResponse getIntentResponse(Session session, Boolean control) {
		
		int session_index = (int) session.getAttribute("SESSION_EVENT_INDEX");
		
		if (control) session_index--;
		else session_index++;
	
		if(session_index >= event_list.size() || session_index <0){
			
			SimpleCard card = new SimpleCard();
			card.setContent("Event list finished");
			card.setTitle("Party Buzz");
			
			PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	        speech.setText("Event list finished");
			
			return SpeechletResponse.newTellResponse(speech, card); 
		}
		
		Event event = new Event();
		
		event = event_list.get(session_index);
		
		String speechOutput = event.getName().getText().toString();
		
		session.setAttribute("SESSION_EVENT_INDEX", session_index);
		

		// Reprompt speech will be triggered if the user doesn't respond.
		String repromptText = "You can say next for next event";

		SimpleCard card = new SimpleCard();
		card.setTitle("Party Buzz");
		card.setContent(speechOutput);
		
		return newAskResponse(speechOutput, false, repromptText, false);
	}
	

	private SpeechletResponse getHelpResponse() {
		String speechText = "Help intent";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("PartyHouse");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
			String repromptText, boolean isRepromptSsml) {

		OutputSpeech outputSpeech, repromptOutputSpeech;

		if (isOutputSsml) {
			outputSpeech = new SsmlOutputSpeech();
			((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
		} else {
			outputSpeech = new PlainTextOutputSpeech();
			((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
		}

		if (isRepromptSsml) {
			repromptOutputSpeech = new SsmlOutputSpeech();
			((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
		} else {
			repromptOutputSpeech = new PlainTextOutputSpeech();
			((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
		}

		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(repromptOutputSpeech);

		return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
	}


	class Events {

		private final int index;
		private final String eventTitle;

		public Events(int index, String eventTitle) {
			super();
			this.index = index;
			this.eventTitle = eventTitle;
		}


	}


}
