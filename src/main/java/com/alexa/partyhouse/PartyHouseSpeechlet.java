package com.alexa.partyhouse;

import java.lang.reflect.Array;
import java.util.ArrayList;

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


public class PartyHouseSpeechlet implements Speechlet{

	private static final Logger log = LoggerFactory.getLogger(PartyHouseSpeechlet.class);

	private ArrayList<String> event_list = new ArrayList<>();

	private static int SESSION_EVENT_INDEX = 0;

	public PartyHouseSpeechlet() {

//		event_list.add(new Events(1, "Event one"));
//		event_list.add(new Events(2, "Event two"));
//		event_list.add(new Events(3, "Event three"));
		
		
		event_list.add("Event one");
		event_list.add("Event two");
		event_list.add("Event three");
	}

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {

		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());

		String speechOutput = "Welcome to party house. You can ask where is the party ?";

		// Reprompt speech will be triggered if the user doesn't respond.
		String repromptText = "You can ask, where is the party";

		SimpleCard card = new SimpleCard();
		card.setTitle("Party House");
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
			return getIntentResponse(session);
		} else if("NextEventIntent".equals(intentName)) {
			return getIntentResponse(session);
		}
		
		else if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else {
			throw new SpeechletException("Invalid Intent");
		}

	}


	public SpeechletResponse getIntentResponse(Session session) {
		
		int session_index = (int) session.getAttribute("SESSION_EVENT_INDEX");
		
		if(session_index >= event_list.size()){
			
			SimpleCard card = new SimpleCard();
			card.setContent("Event list finished");
			card.setTitle("Party House");
			
			PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	        speech.setText("Event list finished");
			
			return SpeechletResponse.newTellResponse(speech, card); 
		}
		
		
		String speechOutput = event_list.get(session_index++);
		
		session.setAttribute("SESSION_EVENT_INDEX", session_index);

		// Reprompt speech will be triggered if the user doesn't respond.
		String repromptText = "You can say next for next event";

		SimpleCard card = new SimpleCard();
		card.setTitle("Party House");
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
