/**
 * 
 */
package de.i3mainz.openballoonmap.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.i3mainz.openballoonmap.exceptions.BalloonAlreadyUsedException;
import de.i3mainz.openballoonmap.exceptions.BalloonNotExistsException;
import de.i3mainz.openballoonmap.model.Find;
import de.i3mainz.openballoonmap.service.DBService;
import de.i3mainz.openballoonmap.service.Utils;

/**
 * @author Nikolai Bock
 * @author Florian Thiery
 *
 */
@RestController
public class FindController {

	@Autowired
	private DBService service;

	@PostMapping("/AddFind")
	public void add(@RequestParam(name = "nr") String nrStr, @RequestParam(name = "location") String location,
			@RequestParam(name = "lon") double lon, @RequestParam(name = "lat") double lat,
			@RequestParam(name = "timestamp") @DateTimeFormat(pattern = "yyyy-MM-dd") Date timestamp,
			@RequestParam(name = "remark", required = false) String remark, HttpServletResponse response)
			throws IOException {
		try {
			response.setContentType("text/html;charset=UTF-8");
			int nr = Utils.codeStringToInt(nrStr);
			Find find = service.insertFind(nr, location, lon, lat, timestamp, remark);
			if (find != null) {
				response.sendRedirect("thx?ballooncode=" + nrStr);
			} else {
				throw new Exception("Failed to execute AddFind");
			}
		} catch (BalloonNotExistsException e) {
			response.sendRedirect("ballooncodeerror");
		} catch (BalloonAlreadyUsedException e) {
			response.sendRedirect("ballooncodeexists?ballooncode=" + nrStr);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			PrintWriter out = response.getWriter();
			e.printStackTrace(out);
		}
	}
}
