/**
 * 
 */
package de.i3mainz.openballoonmap.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.i3mainz.openballoonmap.model.Balloon;

/**
 * @author Nikolai Bock
 *
 */
public interface BalloonRepository extends CrudRepository<Balloon, Long> {

	public List<Balloon> findByEvent_id(int event);
	public Balloon findByNr(int nr);

}
