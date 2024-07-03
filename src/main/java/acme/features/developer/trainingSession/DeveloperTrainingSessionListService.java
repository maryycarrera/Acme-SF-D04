
package acme.features.developer.trainingSession;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		int developerId;
		TrainingModule module;

		masterId = super.getRequest().getData("masterId", int.class);
		module = this.repository.findOneTrainingModuleById(masterId);

		developerId = super.getRequest().getPrincipal().getActiveRoleId();

		status = module != null && developerId == module.getDeveloper().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingSession> sessions;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		sessions = this.repository.findManyTrainingSessionsByModuleId(masterId);

		super.getBuffer().addData(sessions);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriodDate", "finishPeriodDate", "location", "instructor", "contactEmail", "link", "draftMode");
		// super.addPayload(dataset, object, "contactEmail", "link", "draftMode");

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<TrainingSession> objects) {
		assert objects != null;

		int masterId;
		TrainingModule module;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		module = this.repository.findOneTrainingModuleById(masterId);
		showCreate = module.isDraftMode() && super.getRequest().getPrincipal().hasRole(module.getDeveloper());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
