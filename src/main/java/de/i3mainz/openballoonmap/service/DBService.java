/**
 * 
 */
package de.i3mainz.openballoonmap.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import de.i3mainz.openballoonmap.exceptions.BalloonAlreadyUsedException;
import de.i3mainz.openballoonmap.exceptions.BalloonNotExistsException;
import de.i3mainz.openballoonmap.model.Balloon;
import de.i3mainz.openballoonmap.model.Event;
import de.i3mainz.openballoonmap.model.EventBalloon;
import de.i3mainz.openballoonmap.model.Find;
import de.i3mainz.openballoonmap.repository.BalloonRepository;
import de.i3mainz.openballoonmap.repository.EventRepository;
import de.i3mainz.openballoonmap.repository.FindRepository;

/**
 * @author Nikolai Bock
 * @author Martin Unold
 *
 */
@Service
public class DBService {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private BalloonRepository balloonRepository;
	@Autowired
	private FindRepository findRepository;
	private GeometryFactory geomFac = new GeometryFactory();

	public Iterable<Balloon> insertEvent(String name, String location, double lon, double lat, Date timestamp,
			int balloons) throws SQLException {
		Event event = buildEvent(name, location, lon, lat, timestamp, balloons);
		return eventRepository.save(event).getBalloons();
	}

	public Collection<EventBalloon> insertEventAndReturnEventBalloons(String name, String location, double lon,
			double lat, Date timestamp, int balloons) throws SQLException {
		Event event = buildEvent(name, location, lon, lat, timestamp, balloons);
		return this.createEventballoons(eventRepository.save(event));
	}

	private Event buildEvent(String name, String location, double lon, double lat, Date timestamp, int balloons) {
		Event event = new Event();
		event.setName(name);
		event.setDatum(timestamp);
		event.setLocation(location);
		event.setGeom(geomFac.createPoint(new Coordinate(lon, lat)));
		event.setBalloons(Stream.generate(() -> (int) (Utils.MAX_NR * Math.random())).limit(balloons).map(nr -> {
			Balloon balloon = new Balloon();
			balloon.setEvent(event);
			balloon.setNr(nr);
			return balloon;
		}).collect(Collectors.toList()));
		return event;
	}

	public void deleteEvent(String name) throws SQLException {
		eventRepository.deleteByName(name);
	}

	public void deleteAllEvents() {
		eventRepository.deleteAll();
	}

	public Find insertFind(int nr, String location, double lon, double lat, Date timestamp, String remark)
			throws BalloonNotExistsException, BalloonAlreadyUsedException {
		Balloon balloon = balloonRepository.findByNr(nr);
		if (balloon == null) {
			throw new BalloonNotExistsException();
		}
		if (balloon.getFind() != null) {
			throw new BalloonAlreadyUsedException();
		}
		Find find = buildFind(location, lon, lat, timestamp, remark, balloon);
		return findRepository.save(find);
	}

	private Find buildFind(String location, double lon, double lat, Date timestamp, String remark, Balloon balloon) {
		Find find = new Find();
		find.setBalloon(balloon);
		find.setDatum(timestamp);
		find.setGeom(geomFac.createPoint(new Coordinate(lon, lat)));
		find.setLocation(location);
		find.setRemark(remark);
		return find;
	}

	public Event getEvent(String name) throws SQLException {
		return eventRepository.findByName(name);
	}

	public List<Balloon> getBalloons(int event) throws SQLException {
		return balloonRepository.findByEvent_id(event);
	}

	public Find getFinds(int balloon) throws SQLException {
		return findRepository.findByBalloon_id(balloon);
	}

	public Find getBalloonData(int ballooncode) throws SQLException {
		return findRepository.findByBalloon_nr(ballooncode);
	}

	public Collection<EventBalloon> getEventBalloons(String name) throws SQLException {
		return this.createEventballoons(this.getEvent(name));
	}

	private Collection<EventBalloon> createEventballoons(Event event) {
		return event.getBalloons().stream()
				.map(b -> new EventBalloon(Utils.intToCodeString(b.getNr()), event.getName()))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
