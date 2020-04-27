/**
 * 
 */
package controllers;

import org.bson.codecs.configuration.CodecConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mongodb.async.SingleResultCallback;

import models.Event;

import java.lang.StringBuilder;
import java.util.concurrent.CountDownLatch;

/**
 * @author nicky
 *
 */
public class EventHandler extends DefaultHandler {
	private int tryCounter = 0;

	private DataController datCon;

	private Event trecEvent;

	private boolean eventStarted, hasId, hasTitle, hasDes, hasStart, hasEnd, hasType, hasQuery, hasLocations,
			hasInjuries, hasDisplaced, hasFinImpact;

	private String id, title, des, start, end, type;
	private String query;

	private StringBuilder stringBuilder;

	/**
	 * 
	 */
	public EventHandler() {
		eventStarted = false;
		hasId = false;
		hasTitle = false;
		hasDes = false;
		hasStart = false;
		hasEnd = false;
		hasType = false;
		hasQuery = false;
		hasLocations = false;
		hasInjuries = false;
		hasDisplaced = false;
		hasFinImpact = false;

		stringBuilder = new StringBuilder();
		datCon = new DataController();

		id = null;
		title = null;
		des = null;
		start = null;
		end = null;
		type = null;

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) {

		System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("event")) {
			eventStarted = true;
		}
		if (eventStarted) {
			stringBuilder = new StringBuilder();
			if (qName.equalsIgnoreCase("id")) {
				hasId = true;
			}
			if (qName.equalsIgnoreCase("title")) {
				hasTitle = true;
			}
			if (qName.equalsIgnoreCase("description")) {
				hasDes = true;
			}
			if (qName.equalsIgnoreCase("start")) {
				hasStart = true;
			}
			if (qName.equalsIgnoreCase("end")) {
				hasEnd = true;
			}
			if (qName.equalsIgnoreCase("query")) {
				hasQuery = true;
			}
			if (qName.equalsIgnoreCase("type")) {
				hasType = true;
			}

		}
	}

	public void endElement(String uri, String localName, String qName) {

		if (eventStarted) {
			if (hasId) {
				System.out.println("eventId : " + stringBuilder.toString());
				id = stringBuilder.toString();
				hasId = false;
			}
			if (hasTitle) {
				System.out.println("title : " + stringBuilder.toString());
				title = stringBuilder.toString();
				hasTitle = false;
			}
			if (hasDes) {
				System.out.println("description : " + stringBuilder.toString());
				des = stringBuilder.toString();
				hasDes = false;
			}
			if (hasStart) {
				System.out.println("start : " + stringBuilder.toString());
				start = stringBuilder.toString();
				hasStart = false;
			}
			if (hasEnd) {
				System.out.println("end : " + stringBuilder.toString());
				end = stringBuilder.toString();
				hasEnd = false;
			}
			if (hasQuery) {
				System.out.println("query : " + stringBuilder.toString());
				query = stringBuilder.toString();
				hasQuery = false;
			}
			if (hasType) {
				System.out.println("type : " + stringBuilder.toString());
				type = stringBuilder.toString();
				hasType = false;
			}
			if (qName.equalsIgnoreCase("event")) {
				createEvent();
				eventStarted = false;
			}
		}
	}

	public void characters(char ch[], int start, int length) {

		if (stringBuilder != null) {
			for (int i = start; i < start + length; i++) {
				stringBuilder.append(ch[i]);
			}
		}

	};

	private void createEvent() {
		trecEvent = new Event();
		if (id != null)
			try {
				trecEvent.setEventId(Integer.parseInt(id));
			} catch (Exception e) {
				trecEvent.setEventId(0);
			}
		if (title != null)
			trecEvent.setEventTitle(title);
		if (des != null)
			trecEvent.setEventDes(des);
		if (type != null)
			trecEvent.setEventType(type);
		if (query != null)
			trecEvent.setEventQuery(query);
		if (start != null)
			trecEvent.setEventStart(Long.valueOf(start));
		if (end != null)
			trecEvent.setEventEnd(Long.valueOf(end));
		try {
			datCon.insertEvent(trecEvent);
		} catch (CodecConfigurationException e) {
			// TODO: handle exception
			if (tryCounter == 0) {
				datCon = new DataController();
				createEvent();
				tryCounter++;
			} else {
				System.out.println("CodecConfigurationException");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}