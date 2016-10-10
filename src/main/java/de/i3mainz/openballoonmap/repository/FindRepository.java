/**
 * 
 */
package de.i3mainz.openballoonmap.repository;

import org.springframework.data.repository.CrudRepository;

import de.i3mainz.openballoonmap.model.Find;

/**
 * @author Nikolai Bock
 *
 */
public interface FindRepository extends CrudRepository<Find, Long> {

	public Find findByBalloon_id(int balloon);
	public Find findByBalloon_nr(int ballooncode);
}
