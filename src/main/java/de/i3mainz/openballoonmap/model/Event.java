package de.i3mainz.openballoonmap.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.vividsolutions.jts.geom.Point;

/**
 * @author Nikolai Bock
 *
 */
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "event_name")
	private String name;
	@Column(name = "event_timestamp")
	private Date datum;
	private String location;
	@Column(columnDefinition = "Geometry", name = "geom")
	private Point geom;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
	private List<Balloon> balloons;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the datum
	 */
	public Date getDatum() {
		return datum;
	}

	/**
	 * @param datum
	 *            the datum to set
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the geom
	 */
	public Point getGeom() {
		return geom;
	}

	/**
	 * @param geom
	 *            the geom to set
	 */
	public void setGeom(Point geom) {
		this.geom = geom;
	}

	/**
	 * @return the balloons
	 */
	public List<Balloon> getBalloons() {
		return balloons;
	}

	/**
	 * @param balloons the balloons to set
	 */
	public void setBalloons(List<Balloon> balloons) {
		this.balloons = balloons;
	}
}
