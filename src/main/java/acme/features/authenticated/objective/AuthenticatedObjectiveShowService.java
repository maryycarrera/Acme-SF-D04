
package acme.features.authenticated.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.Priority;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuthenticatedObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int objectiveId;
		Objective objective;

		objectiveId = super.getRequest().getData("id", int.class);
		objective = this.repository.findObjectiveById(objectiveId);

		status = objective != null && super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Objective object;
		int masterId;

		masterId = super.getRequest().getData("id", int.class);
		object = this.repository.findObjectiveById(masterId);

		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choicesPriority;
		choicesPriority = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "startTimeDuration", "finishTimeDuration", "link");
		dataset.put("priority", choicesPriority.getSelected().getKey());
		dataset.put("priorities", choicesPriority);

		super.getResponse().addData(dataset);

	}

}
