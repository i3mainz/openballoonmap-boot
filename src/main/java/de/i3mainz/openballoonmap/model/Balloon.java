/**
 * 
 */
package de.i3mainz.openballoonmap.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Nikolai Bock
 *
 */
@Entity
public class Balloon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDREF_event", nullable = false)
	private Event event;
	private int nr;
	private String properties;
	@OneToOne(cascade=CascadeType.ALL, mappedBy="balloon")
	private Find find;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the nr
	 */
	public int getNr() {
		return nr;
	}

	/**
	 * @param nr
	 *            the nr to set
	 */
	public void setNr(int nr) {
		this.nr = nr;
	}

	/**
	 * @return the properties
	 */
	public String getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(String properties) {
		this.properties = properties;
	}

	/**
	 * @return the find
	 */
	public Find getFind() {
		return find;
	}

	/**
	 * @param find the find to set
	 */
	public void setFind(Find find) {
		this.find = find;
	}
}
