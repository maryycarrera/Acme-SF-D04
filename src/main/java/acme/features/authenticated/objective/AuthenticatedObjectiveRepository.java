
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.objectives.Objective;
import acme.entities.projects.Project;

@Repository
public interface AuthenticatedObjectiveRepository extends AbstractRepository {

	@Query("SELECT o FROM Objective o")
	Collection<Objective> findAllObjectives();

	@Query("SELECT o FROM Objective o WHERE o.id =: masterId")
	Objective findObjectiveById(int masterId);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

}
