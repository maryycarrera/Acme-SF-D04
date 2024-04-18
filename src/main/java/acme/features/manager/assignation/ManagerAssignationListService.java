
package acme.features.manager.assignation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.assignations.Assignation;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerAssignationListService extends AbstractService<Manager, Assignation> {

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
		Collection<Assignation> object;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		object = this.repository.findAssignationsByManagerId(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Assignation object) {
		assert object != null;

		Project project;
		UserStory userStory;
		int assignationId;
		Dataset dataset;

		assignationId = object.getId();
		project = this.repository.findOneProjectByAssignationId(assignationId);
		userStory = this.repository.findOneUserStoryByAssignationId(assignationId);

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("project", project.getCode());
		dataset.put("userStory", userStory.getTitle());

		super.getResponse().addData(dataset);
	}

}
