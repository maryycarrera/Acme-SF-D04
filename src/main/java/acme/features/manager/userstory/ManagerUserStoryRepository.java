
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.assignations.Assignation;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.id = :id ORDER BY us.id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findOneManagerById(int id);

	@Query("SELECT a FROM Assignation a WHERE a.userStory.id = :id ORDER BY a.id")
	Collection<Assignation> findAssignationsByUserStoryId(int id);

	@Query("SELECT a.userStory FROM Assignation a WHERE a.project.id = :id ORDER BY a.userStory.id")
	Collection<UserStory> findUserStoriesByProjectId(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findOneProjectById(int id);

}
