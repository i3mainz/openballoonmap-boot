/**
 * 
 */
package de.i3mainz.openballoonmap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.i3mainz.openballoonmap.service.Utils;

/**
 * @author Nikolai Bock
 * @author Florian Thiery
 *
 */
@RestController
public class BalloonController {

	@GetMapping("/getBalloonCodeInteger")
	public int getBalloonCodeInteger(@RequestParam(name = "ballooncode") String ballooncode) {
		return Utils.codeStringToInt(ballooncode);
	}
}
