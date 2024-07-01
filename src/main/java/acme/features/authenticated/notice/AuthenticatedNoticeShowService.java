
package acme.features.authenticated.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AuthenticatedNoticeShowService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeRepository repository;

	// AbstractService<Authenticated, Notice> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int noticeId;
		Notice notice;

		noticeId = super.getRequest().getData("id", int.class);
		notice = this.repository.findOneNoticeById(noticeId);

		status = notice != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Notice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneNoticeById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title", "message", "email", "link");
		String author = object.author();
		dataset.put("author", author);

		super.getResponse().addData(dataset);
	}
}
