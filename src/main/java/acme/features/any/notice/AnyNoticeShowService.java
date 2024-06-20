
package acme.features.any.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AnyNoticeShowService extends AbstractService<Any, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyNoticeRepository repository;

	// AbstractService<Any, Project> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int noticeId;
		Notice notice;

		noticeId = super.getRequest().getData("id", int.class);
		notice = this.repository.findOneNoticeById(noticeId);

		status = notice != null && super.getRequest().getPrincipal().hasRole(Any.class);

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
