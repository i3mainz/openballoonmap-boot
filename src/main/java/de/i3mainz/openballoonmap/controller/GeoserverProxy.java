/**
 * 
 */
package de.i3mainz.openballoonmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Florian Thiery M.Sc.
 * @author Nikolai Bock
 *
 */
@RestController
public class GeoserverProxy {

	@Value("${geoserver.url:http://localhost/geoserver}")
	private String BASEURL;
	@Autowired
	private RestTemplate template;

	@GetMapping(path = "/layer")
	public String layer(@RequestParam(name = "bereich") String bereich, @RequestParam(name = "layer") String layer,
			@RequestParam(name = "attr", required = false) String attr,
			@RequestParam(name = "value", required = false) String value) {

		if (attr == null || value == null) {
			return getAll(bereich, layer);
		} else {
			return getFilterLiteral(bereich, layer, attr, value);
		}
	}

	private String getAll(String arbeitsbereich, String name) {

		String wfsurl = BASEURL + "/" + arbeitsbereich + "/ows?service=WFS&version=1.0.0&request=GetFeature&typeName="
				+ arbeitsbereich + ":" + name;
		wfsurl = wfsurl + "&outputFormat=application/json";

		return template.getForObject(wfsurl, String.class);

	}

	/**
	 * Get all Findspots from Geoserver [Samian:findspot]
	 *
	 * @return String [GeoJSON]
	 */
	private String getFilterLiteral(String arbeitsbereich, String name, String attr, String value) {

		String wfsurl = BASEURL + "/" + arbeitsbereich + "/ows?service=WFS&version=1.0.0&request=GetFeature&typeName="
				+ arbeitsbereich + ":" + name;
		wfsurl = wfsurl + "&outputFormat=json";
		wfsurl = wfsurl + "&Filter=<Filter><PropertyIsEqualTo><PropertyName>" + attr
				+ "</PropertyName><Literal>" + value + "</Literal></PropertyIsEqualTo></Filter>";

		return template.getForObject(wfsurl, String.class);

	}
}
