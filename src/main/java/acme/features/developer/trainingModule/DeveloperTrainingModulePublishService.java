
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	private DeveloperTrainingModuleRepository trainingModuleRepository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.trainingModuleRepository.findTrainingModuleById(masterId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.trainingModuleRepository.findTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.trainingModuleRepository.findProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existe;

			existe = this.trainingModuleRepository.findTrainingModuleByCode(object.getCode());
			super.state(existe == null || existe.equals(object), "code", "developer.training-module.form.error.duplicated");
		}

		if (object.getUpdateMoment() != null && !super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.update-date-not-valid");
		{
			Collection<TrainingSession> trainingSessions;
			int totalTrainingSessions;

			trainingSessions = this.trainingModuleRepository.findAllTrainingSessionsByTrainingModuleId(object.getId());
			totalTrainingSessions = trainingSessions.size();
			super.state(totalTrainingSessions >= 1, "*", "developer.training-module.form.error.not-enough-training-sessions");
			boolean published = true;
			for (TrainingSession c : trainingSessions)
				if (c.isDraftMode()) {
					published = false;
					break;
				}

			if (!published)
				super.state(published, "*", "developer.training-module.form.error.all-training-sessions-must-be-published");
		}
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);
		this.trainingModuleRepository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		projects = this.trainingModuleRepository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "draftMode", "project");
		dataset.put("difficulty", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}

}
