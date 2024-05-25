
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService<Manager, UserStory> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		Project project;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(id);
		status = project != null && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> object;
		int id;
		final boolean showCreate;

		id = super.getRequest().getData("masterId", int.class);
		object = this.repository.findUserStoriesByProjectId(id);
		showCreate = false;

		super.getBuffer().addData(object);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
