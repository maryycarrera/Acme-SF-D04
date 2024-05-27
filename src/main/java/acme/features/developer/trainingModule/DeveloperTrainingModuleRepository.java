
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
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select project from Project project where project.draftMode = false order by project.id")
	Collection<Project> findAllProjects();

	@Query("select trainingModule from TrainingModule trainingModule where trainingModule.developer.id = :developerId order by trainingModule.id")
	List<TrainingModule> findAllTrainingModulesByDeveloperId(int developerId);

	@Query("select trainingModule.details from TrainingModule trainingModule where trainingModule.developer.id = :developerId order by trainingModule.id")
	List<String> findAllTrainingModuleDetailsByDeveloperId(int developerId);

	@Query("select trainingModule from TrainingModule trainingModule where trainingModule.id = :trainingModuleId")
	TrainingModule findTrainingModuleById(int trainingModuleId);

	@Query("select developer from Developer developer where developer.id = :developerId")
	Developer findDeveloperById(int developerId);

	@Query("select project from Project project where project.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select trainingModule from TrainingModule trainingModule where trainingModule.code = :trainingModuleCode")
	TrainingModule findTrainingModuleByCode(String trainingModuleCode);

	@Query("select trainingSession from TrainingSession trainingSession where trainingSession.trainingModule.id = :trainingModuleId order by trainingSession.id")
	Collection<TrainingSession> findAllTrainingSessionsByTrainingModuleId(int trainingModuleId);

}
