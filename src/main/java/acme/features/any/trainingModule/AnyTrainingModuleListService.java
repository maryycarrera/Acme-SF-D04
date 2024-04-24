
package acme.features.any.trainingModule;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;

@Service
public class AnyTrainingModuleListService extends AbstractService<Any, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Any.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingModule> object;
		object = this.repository.findPublishedTrainingModules();
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		final Dataset dataset = super.unbind(object, "code");

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
