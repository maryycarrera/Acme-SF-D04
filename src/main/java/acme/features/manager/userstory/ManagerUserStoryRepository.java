
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.userstories.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("SELECT DISTINCT pus.userStory FROM ProjectUserStory pus WHERE pus.project.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

}
