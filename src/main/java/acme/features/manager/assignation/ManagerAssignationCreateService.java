
package acme.features.manager.assignation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.assignations.Assignation;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerAssignationCreateService extends AbstractService<Manager, Assignation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAssignationRepository repository;

	// AbstractService<Manager, Assignation> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Assignation object;

		object = new Assignation();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Assignation object) {
		assert object != null;

		super.bind(object, "userStory", "project");
	}

	@Override
	public void validate(final Assignation object) {
		assert object != null;
		Project project;
		UserStory userStory;

		project = object.getProject();
		userStory = object.getUserStory();

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Assignation existing;

			existing = this.repository.findOneAssignationByProjectIdAndUserStoryId(project.getId(), userStory.getId());
			super.state(existing == null, "project", "manager.assignation.form.error.existing-project-assignation");

			super.state(project.isDraftMode() || !userStory.isDraftMode(), "project", "manager.assignation.form.error.published-project");
		}

		if (!super.getBuffer().getErrors().hasErrors("userStory")) {
			Assignation existing;

			existing = this.repository.findOneAssignationByProjectIdAndUserStoryId(project.getId(), userStory.getId());
			super.state(existing == null, "userStory", "manager.assignation.form.error.existing-project-assignation");

			super.state(project.isDraftMode() || !userStory.isDraftMode(), "userStory", "manager.assignation.form.error.published-project");
		}

	}

	@Override
	public void perform(final Assignation object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Assignation object) {
		assert object != null;

		Collection<UserStory> userStories;
		Collection<Project> projects;
		SelectChoices choicesUS;
		SelectChoices choicesP;
		Dataset dataset;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		userStories = this.repository.findUserStoriesByManagerId(managerId);
		choicesUS = SelectChoices.from(userStories, "title", object.getUserStory());

		projects = this.repository.findProjectsByManagerId(managerId);
		choicesP = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("userStory", choicesUS.getSelected().getKey());
		dataset.put("userStories", choicesUS);
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}

}
