
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.assignations.Assignation;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findOneManagerById(int id);

	@Query("SELECT a FROM Assignation a WHERE a.userStory.id = :id")
	Collection<Assignation> findAssignationsByUserStoryId(int id);

}
