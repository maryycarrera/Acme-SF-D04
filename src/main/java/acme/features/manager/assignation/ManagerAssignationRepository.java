
package acme.features.manager.assignation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.assignations.Assignation;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerAssignationRepository extends AbstractRepository {

	@Query("SELECT a FROM Assignation a WHERE a.id = :id")
	Assignation findOneAssignationById(int id);

	@Query("SELECT a FROM Assignation a WHERE a.project.manager.id = :id AND a.userStory.manager.id = :id")
	Collection<Assignation> findAssignationsByManagerId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findOneManagerById(int id);

	@Query("SELECT a FROM Assignation a WHERE a.project.id = :id")
	Collection<Assignation> findAssignationsByProjectId(int id);

	@Query("SELECT a FROM Assignation a WHERE a.userStory.id = :id")
	Collection<Assignation> findAssignationsByUserStoryId(int id);

	@Query("SELECT a FROM Assignation a WHERE a.project.id = :projectId AND a.userStory.id = :userStoryId")
	Assignation findOneAssignationByProjectIdAndUserStoryId(int projectId, int userStoryId);

	@Query("SELECT us FROM UserStory us WHERE us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("SELECT a.project FROM Assignation a WHERE a.id = :id")
	Project findOneProjectByAssignationId(int id);

	@Query("SELECT a.userStory FROM Assignation a WHERE a.id = :id")
	UserStory findOneUserStoryByAssignationId(int id);

	@Query("SELECT a.project.manager FROM Assignation a WHERE a.id = :id")
	Manager findOneManagerByAssignationId(int id);

}
