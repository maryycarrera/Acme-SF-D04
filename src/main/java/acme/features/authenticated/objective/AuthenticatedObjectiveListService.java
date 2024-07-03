
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objectives.Objective;

@Service
public class AuthenticatedObjectiveListService extends AbstractService<Authenticated, Objective> {

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
		Collection<Objective> objectives;

		objectives = this.repository.findAllObjectives();

		super.getBuffer().addData(objectives);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title", "priority");

		super.addPayload(dataset, object, "description", "status", "startTimeDuration", "finishTimeDuration", "link");
		super.getResponse().addData(dataset);
	}

}
