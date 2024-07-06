
package acme.features.administrator.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.Priority;

@Service
public class AdministratorObjectiveCreateService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Objective object;

		object = new Objective();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "description", "priority", "status", "startTimeDuration", "finishTimeDuration", "link");

	}

	@Override
	public void validate(final Objective object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("finishTimeDuration"))
			super.state(MomentHelper.isBefore(object.getStartTimeDuration(), object.getFinishTimeDuration()), "finishTimeDuration", "administrator.objective.form.error.low-period");
		if (!super.getBuffer().getErrors().hasErrors("startTimeDuration"))
			super.state(MomentHelper.isBefore(object.getInstantiationMoment(), object.getStartTimeDuration()), "startTimeDuration", "administrator.objective.form.error.istantiation-after-start");
		if (!super.getBuffer().getErrors().hasErrors("confirm")) {
			final boolean confirm = super.getRequest().getData("confirm", boolean.class);

			super.state(confirm, "confirm", "administrator.objective.form.error.not-confirmed");
		}

	}

	@Override
	public void perform(final Objective object) {
		assert object != null;

		this.repository.save(object);
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
		dataset.put("confirm", false);

		super.getResponse().addData(dataset);
	}
}
