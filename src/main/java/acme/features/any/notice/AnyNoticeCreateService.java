
package acme.features.any.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AnyNoticeCreateService extends AbstractService<Any, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Notice object;

		object = new Notice();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;

		Date instantiationMoment;

		instantiationMoment = MomentHelper.getCurrentMoment();
		super.bind(object, "title", "author", "message", "email", "link");
		object.setInstantiationMoment(instantiationMoment);
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;
		boolean isAccepted;

		isAccepted = this.getRequest().getData("accept", boolean.class);
		super.state(isAccepted, "accept", "anonymous.user-account.form.error.must-accept");

	}

	@Override
	public void perform(final Notice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "author", "message", "email", "link");

		if (super.getRequest().getMethod().equals("POST"))
			dataset.put("accept", super.getRequest().getData("accept", boolean.class));
		else
			dataset.put("accept", "false");

		super.getResponse().addData(dataset);

	}
}
