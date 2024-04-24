
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.TrainingModule;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select trainingModule from TrainingModule trainingModule where trainingModule.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select trainingModule from TrainingModule trainingModule where trainingModule.draftMode = false")
	Collection<TrainingModule> findPublishedTrainingModules();

	@Query("select project from Project project")
	Collection<Project> findAllProjects();

}
