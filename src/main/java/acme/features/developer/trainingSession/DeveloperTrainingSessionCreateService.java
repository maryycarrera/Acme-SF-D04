
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession session;
		int masterId;
		TrainingModule module;

		masterId = super.getRequest().getData("masterId", int.class);
		module = this.repository.findOneTrainingModuleById(masterId);

		session = new TrainingSession();
		session.setTrainingModule(module);

		super.getBuffer().addData(session);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriodDate", "finishPeriodDate", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishPeriodDate"))
			super.state(object.getStartPeriodDate() != null && object.getFinishPeriodDate() != null && MomentHelper.isAfter(object.getFinishPeriodDate(), object.getStartPeriodDate())
				&& MomentHelper.isLongEnough(object.getStartPeriodDate(), object.getFinishPeriodDate(), 7, ChronoUnit.DAYS), "finishPeriodDate", "developer.training-session.form.error.too-close");

		if (!super.getBuffer().getErrors().hasErrors("startPeriodDate"))
			super.state(object.getStartPeriodDate() != null && MomentHelper.isAfter(object.getStartPeriodDate(), object.getTrainingModule().getCreationMoment())
				&& MomentHelper.isLongEnough(object.getTrainingModule().getCreationMoment(), object.getStartPeriodDate(), 7, ChronoUnit.DAYS), "startPeriodDate", "developer.training-session.form.error.start-too-early");
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriodDate", "finishPeriodDate", "location", "instructor", "contactEmail", "link", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
