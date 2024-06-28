
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.Priority;
import acme.entities.projects.Project;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuthenticatedObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Objective object;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findObjectiveById(masterId);

		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		SelectChoices choicesPriority;
		choicesPriority = SelectChoices.from(Priority.class, object.getPriority());

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "startTimeDuration", "finishTimeDuration", "link");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("priority", choicesPriority.getSelected().getKey());
		dataset.put("priorities", choicesPriority);

		super.getResponse().addData(dataset);

	}

}
