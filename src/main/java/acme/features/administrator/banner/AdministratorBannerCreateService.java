
package acme.features.administrator.banner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banners.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService<Administrator, Banner> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "startDate", "finishDate", "picture", "slogan", "targetWebDocument");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		Date instantiation = object.getInstantiationMoment();
		Date start = object.getStartDate();
		Date finish = object.getFinishDate();

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(start.after(instantiation), //
				"code", "administrator.banner.form.error.must-start-after-instantiation");

		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			super.state(finish.after(start), "finishDate", "administrator.banner.form.error.must-finish-after-start");
			if (finish.after(start)) {
				long diffInMillies = Math.abs(finish.getTime() - start.getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				super.state(diff >= 7, "finishDate", "administrator.banner.form.error.display-period-too-short");
			}
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "startDate", "finishDate", "picture", "slogan", "targetWebDocument");

		super.getResponse().addData(dataset);
	}

}
