
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Repository
public interface TrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	List<TrainingModule> findAllTrainingModuleByDeveloperId(int developerId);

	@Query("select t.details from TrainingModule t where t.developer.id = :developerId")
	List<String> findAllTrainingModuleDetailsByDevId(int developerId);

	@Query("select t from TrainingModule t where t.id = :tmId")
	TrainingModule findOneTrainingModuleById(int tmId); //Hecho

	@Query("select d from Developer d where d.id = :developerId")
	Developer findOneDeveloperById(int developerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId); //Hecho

	@Query("select t from TrainingModule t where t.code = :tmCode")
	TrainingModule findOneTrainingModuleByCode(String tmCode); //Hecho

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :tmId")
	Collection<TrainingSession> findAllTrainingSessionByTrainingModuleId(int tmId); //Hecho

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

}
