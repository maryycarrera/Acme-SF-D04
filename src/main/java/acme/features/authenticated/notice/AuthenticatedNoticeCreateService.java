
package acme.features.authenticated.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AuthenticatedNoticeCreateService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Notice object;

		Date instantiationMoment;
		String username;
		String fullName;

		instantiationMoment = MomentHelper.getCurrentMoment();
		username = super.getRequest().getPrincipal().getUsername();
		fullName = this.repository.findOneUserAccountByUsername(username).getIdentity().getFullName();

		object = new Notice();
		object.setInstantiationMoment(instantiationMoment);
		object.setUsername(username);
		object.setFullName(fullName);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;
		super.bind(object, "title", "message", "email", "link");
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;
		boolean isAccepted;

		if (!super.getBuffer().getErrors().hasErrors("author")) {
			int length = object.author().length();
			super.state(length < 76, "*", "authenticated.notice.form.error.too-long");
			super.state(length > 3, "*", "authenticated.notice.form.error.blank");
		}

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

		dataset = super.unbind(object, "title", "message", "email", "link");

		if (super.getRequest().getMethod().equals("POST"))
			dataset.put("accept", super.getRequest().getData("accept", boolean.class));
		else
			dataset.put("accept", "false");

		super.getResponse().addData(dataset);

	}
}
