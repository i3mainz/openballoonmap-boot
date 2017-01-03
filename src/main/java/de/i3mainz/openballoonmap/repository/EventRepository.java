/**
 * 
 */
package de.i3mainz.openballoonmap.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import de.i3mainz.openballoonmap.model.Event;

/**
 * @author Nikolai Bock
 *
 */
public interface EventRepository extends CrudRepository<Event, Long> {

	public Event findByName(String name);

	@Transactional
	public void deleteByName(String name);

}
