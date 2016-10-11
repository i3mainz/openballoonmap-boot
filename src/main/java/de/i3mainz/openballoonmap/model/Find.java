/**
 * 
 */
package de.i3mainz.openballoonmap.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.vividsolutions.jts.geom.Point;

/**
 * @author Nikolai Bock
 *
 */
@Entity
public class Find {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDREF_balloon", nullable = false)
	private Balloon balloon;
	@Column(name = "find_timestamp")
	private Date datum;
	private String location;
	@Column(columnDefinition = "Geometry", name = "geom")
	private Point geom;
	private String remark;

	/**
	 * @return the balloon
	 */
	public Balloon getBalloon() {
		return balloon;
	}

	/**
	 * @param balloon
	 *            the balloon to set
	 */
	public void setBalloon(Balloon balloon) {
		this.balloon = balloon;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

}
