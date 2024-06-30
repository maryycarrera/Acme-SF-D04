
package acme.features.any.trainingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int trainingModuleId;
		TrainingModule trainingModule;

		trainingModuleId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findTrainingModuleById(trainingModuleId);

		status = trainingModule != null && !trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "draftMode", "project");

		super.getResponse().addData(dataset);
	}

}
