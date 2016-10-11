package de.i3mainz.openballoonmap.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.i3mainz.openballoonmap.converter.CSVMessageConverter;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addRedirectViewController("/", "/map");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/createEvent").setViewName("event");
        registry.addViewController("/ballooncodeerror").setViewName("ballooncodeerror");
        registry.addViewController("/find").setViewName("find");
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new CSVMessageConverter());
    }

}
