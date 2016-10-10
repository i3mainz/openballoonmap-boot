/**
 * 
 */
package de.i3mainz.openballoonmap.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.i3mainz.openballoonmap.model.Balloon;
import de.i3mainz.openballoonmap.model.Event;
import de.i3mainz.openballoonmap.service.DBService;
import de.i3mainz.openballoonmap.service.Utils;

/**
 * @author Nikolai Bock
 *
 */
@RestController
public class EventController {

	@Autowired
	private DBService service;

	@PostMapping(path = "/AddEvent")
	public void add(@RequestParam(name = "name") String name,
			@RequestParam(name = "location") String location, @RequestParam(name = "lon") double lon,
			@RequestParam(name = "lat") double lat,
			@RequestParam(name = "timestamp") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date timestamp,
			@RequestParam(name = "balloons") int balloons, HttpServletResponse response) throws Exception {
		
		if (balloons > 1000) {
			throw new Exception("too many: " + balloons);
		}
		 writeCSV(service.insertEvent(name, location, lon, lat, timestamp,
		 balloons), response, name);
//		Collection<Map<String, Object>> balloonCollection = new ArrayList<>();
//		service.insertEvent(name, location, lon, lat, timestamp, balloons).forEach(b -> {
//			Map<String, Object> tmp = new HashMap<>();
//			tmp.put("id", b.getId());
//			tmp.put("event", name);
//			balloonCollection.add(tmp);
//		});
//
//		return balloonCollection;

	}

	@GetMapping("/Get.csv")
	public void getCSV(@RequestParam(name = "event") String name, HttpServletResponse response)
			throws IOException, SQLException {
		Event event = service.getEvent(name);

		if (event != null) {
			writeCSV(service.getBalloons(event.getId()), response, name);
		}

	}

	private void writeCSV(Iterable<Balloon> balloons, HttpServletResponse response, String name) throws IOException {
		response.setContentType("text/csv;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("id;event");
			Event event = service.getEvent(name);
			if (event != null) {
				balloons.forEach(b -> out.println(Utils.intToCodeString(b.getNr()) + ";" + name));
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + name + ".csv");
		} catch (Exception e) {
			response.resetBuffer();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			e.printStackTrace(out);
		} finally {
			out.close();
		}
	}

}
