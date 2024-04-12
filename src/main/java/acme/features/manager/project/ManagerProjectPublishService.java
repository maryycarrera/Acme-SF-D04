
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Project project;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(masterId);
		manager = project == null ? null : project.getManager();
		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);
		object.setDraftMode(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractDescription", "hasFatalErrors", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		Collection<UserStory> userStories;

		userStories = this.repository.findUserStoriesByProjectId(object.getId());

		if (!super.getBuffer().getErrors().hasErrors("hasFatalErrors")) {
			Project existing;

			existing = this.repository.findOneProjectByCode(object.getCode());
			super.state(!existing.isHasFatalErrors(), "hasFatalErrors", "manager.project.form.error.hasFatalErrors");
		}

		if (!super.getBuffer().getErrors().hasErrors()) {
			super.state(!userStories.isEmpty(), "code", "manager.project.form.error.no-user-stories");
			super.state(userStories.stream().allMatch(us -> !us.isDraftMode()), "code", "manager.project.form.error.user-stories-not-published");
		}

		if (super.getBuffer().getErrors().hasErrors())
			object.setDraftMode(true);
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractDescription", "hasFatalErrors", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);

	}

}
