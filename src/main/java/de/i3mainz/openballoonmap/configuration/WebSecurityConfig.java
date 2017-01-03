package de.i3mainz.openballoonmap.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Build the request matcher for CSFR
	RequestMatcher csrfRequestMatcher = new RequestMatcher() {

		private RegexRequestMatcher requestMatcher = new RegexRequestMatcher("/events/**", null);

		@Override
		public boolean matches(HttpServletRequest request) {

			if (requestMatcher.matches(request))
				return false;

			return true;
		}

	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and().authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/img/**", "/elements/**", "/webjars/**").permitAll()
				.antMatchers("/", "/map", "/find", "/AddFind", "/layer", "/ballooncodeerror", "/ballooncodeexists",
						"/thx")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll();
	}
}