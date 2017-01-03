/**
 * 
 */
package de.i3mainz.openballoonmap.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.i3mainz.openballoonmap.model.Event;
import de.i3mainz.openballoonmap.model.Find;
import de.i3mainz.openballoonmap.service.DBService;
import de.i3mainz.openballoonmap.service.Utils;

/**
 * @author Nikolai Bock
 * @author Florian Thiery
 *
 */
@Controller
public class MainController {

	@Autowired
	private DBService service;

	@Value("${geoserver.url:http://localhost/geoserver}")
	private String BASEURL;

	@GetMapping("/map")
	public String map(@RequestParam(name = "ballooncode", required = false) String bc,
			@RequestParam(name = "event", required = false) String ev, Model model) {

		model.addAttribute("geoserverURL", BASEURL);

		try {
			if (bc != null && !bc.isEmpty()) {
				Find data = service.getBalloonData(Utils.codeStringToInt(bc));

				if (data != null) {
					model.addAttribute("event", data.getBalloon().getEvent().getName());
					model.addAttribute("lat", data.getGeom().getCentroid().getY());
					model.addAttribute("lon", data.getGeom().getCentroid().getX());
					model.addAttribute("function", "showBalloon");
					model.addAttribute("nr", Utils.codeStringToInt(bc));
				} else {
					model.addAttribute("function", "noBalloon");
				}
			} else if (ev != null && !ev.isEmpty()) {
				Event event = service.getEvent(ev);
				if (event != null) {
					model.addAttribute("event", ev);
					model.addAttribute("function", "showEvent");
				} else {
					model.addAttribute("function", "noEvent");
				}
			}

		} catch (SQLException e) {
			return "error";
		}
		return "map";
	}

	@GetMapping("/thx")
	public String thx(@RequestParam(name = "ballooncode") String bc, Model model) {
		model.addAttribute("ballooncode", bc);
		return "thx";
	}

	@GetMapping("/ballooncodeexists")
	public String ballooncodeexists(@RequestParam(name = "ballooncode") String bc, Model model) {
		model.addAttribute("ballooncode", bc);
		return "ballooncodeexists";
	}

}
