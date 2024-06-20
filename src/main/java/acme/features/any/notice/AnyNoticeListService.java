
package acme.features.any.notice;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AnyNoticeListService extends AbstractService<Any, Notice> {

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

		super.getResponse().addData(dataset);
	}
}
