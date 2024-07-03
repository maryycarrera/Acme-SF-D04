
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
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int id;
		int developerId;
		TrainingModule module;
		TrainingSession session;

		id = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleByTrainingSessionId(id);
		session = this.repository.findOneTrainingSessionById(id);

		developerId = super.getRequest().getPrincipal().getActiveRoleId();

		boolean moduleValid = module != null && module.isDraftMode() && developerId == module.getDeveloper().getId();
		boolean sessionValid = session != null && session.isDraftMode() && developerId == session.getTrainingModule().getDeveloper().getId();

		status = moduleValid && sessionValid;

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

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriodDate", "finishPeriodDate", "location", "instructor", "contactEmail", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
