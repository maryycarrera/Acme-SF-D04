
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		TrainingModule module;
		TrainingSession session;

		sessionId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleByTrainingSessionId(sessionId);
		session = this.repository.findOneTrainingSessionById(sessionId);
		status = module != null && module.isDraftMode() && super.getRequest().getPrincipal().hasRole(module.getDeveloper()) && session.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession session;
		int id;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTrainingSessionById(id);

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
			super.state(existing == null || existing.equals(object), "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startPeriodDate")) {
			TrainingModule module;
			int id;

			id = super.getRequest().getData("id", int.class);
			module = this.repository.findOneTrainingModuleByTrainingSessionId(id);

			Date minimumStart = MomentHelper.deltaFromMoment(module.getCreationMoment(), 7, ChronoUnit.DAYS);

			boolean isStartValid = object.getStartPeriodDate() != null && object.getStartPeriodDate().after(minimumStart);
			super.state(isStartValid, "startPeriodDate", "developer.training-session.form.error.creation-moment-invalid");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishsPeriodDate")) {
			super.state(object.getStartPeriodDate() != null && object.getFinishPeriodDate() != null && object.getFinishPeriodDate().after(object.getStartPeriodDate()), "finishPeriodDate", "developer.training-session.form.error.finish-before-start");

			if (object.getStartPeriodDate() != null && object.getFinishPeriodDate() != null && object.getFinishPeriodDate().after(object.getStartPeriodDate())) {
				Date minimumDeadline = MomentHelper.deltaFromMoment(object.getStartPeriodDate(), 7, ChronoUnit.DAYS);
				super.state(object.getFinishPeriodDate().after(minimumDeadline), "finishPeriodDate", "developer.training-session.form.error.too-close");
			}
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriodDate", "finishPeriodDate", "location", "instructor", "contactEmail", "link", "draftMode");
		dataset.put("masterId", object.getTrainingModule().getId());

		super.getResponse().addData(dataset);
	}

}
