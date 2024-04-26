
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
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		TrainingModule module;

		sessionId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleByTrainingSessionId(sessionId);
		status = module != null && !module.isDraftMode() && super.getRequest().getPrincipal().hasRole(module.getDeveloper());

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
			super.state(MomentHelper.isAfter(object.getStartPeriodDate(), module.getCreationMoment()), "startPeriodDate", "developer.training-session.form.error.creation-moment-invalid");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishPeriodDate")) {
			Date minimumEnd;

			minimumEnd = MomentHelper.deltaFromMoment(object.getStartPeriodDate(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getFinishPeriodDate(), minimumEnd), "finishPeriodDate", "developer.training-session.form.error.too-close");
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriodDate", "finishPeriodDate", "location", "instructor", "contactEmail", "link", "published");

		super.getResponse().addData(dataset);
	}

}
