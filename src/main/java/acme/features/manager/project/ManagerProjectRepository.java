
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.assignations.Assignation;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :id ORDER BY p.id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findOneManagerById(int id);

	@Query("SELECT p FROM Project p WHERE p.code = :code")
	Project findOneProjectByCode(String code);

	@Query("SELECT a FROM Assignation a WHERE a.project.id = :id ORDER BY a.id")
	Collection<Assignation> findAssignationsByProjectId(int id);

	@Query("SELECT DISTINCT a.userStory FROM Assignation a WHERE a.project.id = :id ORDER BY a.userStory.id")
	Collection<UserStory> findUserStoriesByProjectId(int id);

}
