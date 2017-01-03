package de.i3mainz.openballoonmap.configuration;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Build the request matcher for CSFR
		RequestMatcher csrfRequestMatcher = new RequestMatcher() {

			// Always allow the HTTP GET method
			private Pattern allowedMethods = Pattern.compile("^GET$");
			
			// Disable CSFR protection on the following urls:
			private AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/events/**") };

			@Override
			public boolean matches(HttpServletRequest request) {
				
			    // Skip allowed methods
			    if (allowedMethods.matcher(request.getMethod()).matches()) {
			      return false;
			    }   

				for (AntPathRequestMatcher rm : requestMatchers) {
					if (rm.matches(request)) {
						return false;
					}
				}

				return true;
			}

		};

		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and().authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/img/**", "/elements/**", "/webjars/**").permitAll()
				.antMatchers("/", "/map", "/find", "/AddFind", "/layer", "/ballooncodeerror", "/ballooncodeexists",
						"/thx")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll();
	}
}