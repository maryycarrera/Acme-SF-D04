
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.objectives.Objective;

@Repository
public interface AuthenticatedObjectiveRepository extends AbstractRepository {

	@Query("SELECT o FROM Objective o")
	Collection<Objective> findAllObjectives();

	@Query("SELECT o FROM Objective o WHERE o.id = :id")
	Objective findObjectiveById(int id);

}
