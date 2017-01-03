/**
 * 
 */
package de.i3mainz.openballoonmap.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.i3mainz.openballoonmap.model.EventBalloon;
import de.i3mainz.openballoonmap.service.DBService;

/**
 * @author Nikolai Bock
 * @author Florian Thiery
 */
@RestController
public class EventController {

	@Autowired
	private DBService service;

	@PostMapping(path = "/AddEvent", produces = "text/csv")
	public Collection<EventBalloon> add(@RequestParam(name = "name") String name,
			@RequestParam(name = "location") String location, @RequestParam(name = "lon") double lon,
			@RequestParam(name = "lat") double lat,
			@RequestParam(name = "timestamp") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date timestamp,
			@RequestParam(name = "balloons") int balloons, HttpServletResponse response) throws Exception {

		if (balloons > 1000) {
			throw new Exception("too many: " + balloons);
		}
		return service.insertEventAndReturnEventBalloons(name, location, lon, lat, timestamp, balloons);
	}

	@GetMapping(path = "/Get.csv", produces = "text/csv")
	public Collection<EventBalloon> getCSV(@RequestParam(name = "event") String name, HttpServletResponse response)
			throws IOException, SQLException {

		return service.getEventBalloons(name);

	}

	@PostMapping(path = "/clearEvent")
	public String delete(@RequestParam(name = "name") String name) throws Exception {
		service.deleteEvent(name);
		return "redirect:/map";
	}

	@PostMapping(path = "/clearAllEvents")
	public String deleteAll() throws Exception {
		service.deleteAllEvents();
		return "redirect:/map";
	}
}
