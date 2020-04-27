/**
 * 
 */
package models;

import java.util.Map;
import org.bson.types.ObjectId;

/**
 * @author nicky
 *
 */
public final class Event {
	private ObjectId id;
	private int eventId;
	private String eventTitle, eventDes, eventType;
	private Long eventStart, eventEnd;
	private String eventQuery;

	private Map<String, String> eventEntities; // Public figures and their mentions
	private Map<String, Integer> eventDeathMap, eventInjuryMap;
	private int eventLevel, eventDeaths, eventInjuries;

	/**
	 * 
	 */
	public Event() {
		// TODO Auto-generated constructor stub
		this.id = new ObjectId();
	}

	public Event(int eventId, String eventTitle, String eventDes, String eventType, Long eventStart, Long eventEnd,
			String eventQuery) {
		super();
		this.id = new ObjectId();
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.eventDes = eventDes;
		this.eventType = eventType;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
		this.eventQuery = eventQuery;
	}

	// Id Specific
	/**
	 * Returns the id
	 *
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the id
	 *
	 * @param id the id
	 */
	public void setId(final ObjectId id) {
		this.id = id;
	}

	// Getters & Setters

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDes() {
		return eventDes;
	}

	public void setEventDes(String eventDes) {
		this.eventDes = eventDes;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Long getEventStart() {
		return eventStart;
	}

	public void setEventStart(Long eventStart) {
		this.eventStart = eventStart;
	}

	public Long getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(Long eventEnd) {
		this.eventEnd = eventEnd;
	}

	public String getEventQuery() {
		return eventQuery;
	}

	public void setEventQuery(String eventQuery) {
		this.eventQuery = eventQuery;
	}

	public Map<String, String> getEventEntities() {
		return eventEntities;
	}

	public void setEventEntities(Map<String, String> eventEntities) {
		this.eventEntities = eventEntities;
	}

	public Map<String, Integer> getEventDeathMap() {
		return eventDeathMap;
	}

	public void setEventDeathMap(Map<String, Integer> eventDeathMap) {
		this.eventDeathMap = eventDeathMap;
	}

	public Map<String, Integer> getEventInjuryMap() {
		return eventInjuryMap;
	}

	public void setEventInjuryMap(Map<String, Integer> eventInjuryMap) {
		this.eventInjuryMap = eventInjuryMap;
	}

	public int getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(int eventLevel) {
		this.eventLevel = eventLevel;
	}

	public int getEventDeaths() {
		return eventDeaths;
	}

	public void setEventDeaths(int eventDeaths) {
		this.eventDeaths = eventDeaths;
	}

	public int getEventInjuries() {
		return eventInjuries;
	}

	public void setEventInjuries(int eventInjuries) {
		this.eventInjuries = eventInjuries;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Event event = (Event) o;

		if (getEventType() != event.getEventType()) {
			return false;
		}
		if (getId() != null ? !getId().equals(event.getId()) : event.getId() != null) {
			return false;
		}
		if (getEventTitle() != null ? !getEventTitle().equals(event.getEventTitle()) : event.getEventTitle() != null) {
			return false;
		}
		if (getEventStart() != null ? !getEventStart().equals(event.getEventStart()) : event.getEventStart() != null) {
			return false;
		}
		if (getEventEnd() != null ? !getEventEnd().equals(event.getEventEnd()) : event.getEventEnd() != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result + (getEventTitle() != null ? getEventTitle().hashCode() : 0);
		result = 31 * result + (getEventStart() != null ? getEventStart().hashCode() : 0);
		result = 31 * result + (getEventType() != null ? getEventType().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Event{" + "id='" + id + "'" + "," + "eventId='" + eventId + "'" + ", title='" + eventTitle + "'"
				+ ", startTime=" + eventStart + ", endTime=" + eventEnd + ", type=" + eventType + ", keywords="
				+ eventQuery + "}";
	}
}
