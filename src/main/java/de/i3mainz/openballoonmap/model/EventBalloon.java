/**
 * 
 */
package de.i3mainz.openballoonmap.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Nikolai Bock
 *
 */
@JsonPropertyOrder({"id", "event"})
public class EventBalloon {
	
	private String id;
	private String event;
	
	
	public EventBalloon(String id, String event) {
		super();
		this.id = id;
		this.event = event;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

}
