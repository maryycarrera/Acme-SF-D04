
package acme.features.authenticated.notice;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AuthenticatedNoticeListService extends AbstractService<Authenticated, Notice> {

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
		Collection<Notice> objects;

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2022);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.DAY_OF_MONTH, 30);

		Date oneMonthAgo = calendar.getTime();

		objects = this.repository.findNoticesWithinOneMonth(oneMonthAgo);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title");
		String author = object.author();
		dataset.put("author", author);
		super.addPayload(dataset, object, "message", "email", "link");

		super.getResponse().addData(dataset);
	}
}
