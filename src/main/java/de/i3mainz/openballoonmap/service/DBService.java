/**
 * 
 */
package de.i3mainz.openballoonmap.service;

import java.sql.SQLException;
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
import de.i3mainz.openballoonmap.model.Find;
import de.i3mainz.openballoonmap.repository.BalloonRepository;
import de.i3mainz.openballoonmap.repository.EventRepository;
import de.i3mainz.openballoonmap.repository.FindRepository;

/**
 * @author Nikolai Bock
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
		Event event = new Event();
		event.setName(name);
		event.setDatum(timestamp);
		event.setLocation(location);
		event.setGeom(geomFac.createPoint(new Coordinate(lat, lon)));
		event.setBalloons(Stream.generate(() -> (int) (Utils.MAX_NR * Math.random())).limit(balloons).map(nr -> {
			Balloon balloon = new Balloon();
			balloon.setEvent(event);
			balloon.setNr(nr);
			return balloon;
		}).collect(Collectors.toList()));
		return eventRepository.save(event).getBalloons();
	}

	public void deleteEvent(String name) throws SQLException {
		eventRepository.deleteByName(name);
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
		Find find = new Find();
		find.setBalloon(balloon);
		find.setDatum(timestamp);
		find.setGeom(geomFac.createPoint(new Coordinate(lat, lon)));
		find.setLocation(location);
		find.setRemark(remark);
		return findRepository.save(find);
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
}
