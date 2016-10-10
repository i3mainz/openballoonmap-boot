/**
 * 
 */
package de.i3mainz.openballoonmap.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
public class MainConfiguration {
	
	@Bean
	public RestTemplate template(){
		return new RestTemplate();
	}
}
