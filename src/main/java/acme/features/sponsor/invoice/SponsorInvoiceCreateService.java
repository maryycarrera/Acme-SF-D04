
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemconfigurations.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		Money dinero = new Money();

		dinero.setAmount(0.0);
		dinero.setCurrency("EUR");

		object = new Invoice();
		object.setCode("");
		object.setRegistrationTime(new Date());
		object.setDueDate(new Date());
		object.setQuantity(dinero);
		object.setTax(0.0);
		object.setLink("");
		object.setSponsorship(sponsorship);
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}
	public boolean isCurrencyAccepted(final Money moneda) {
		SystemConfiguration moneys;
		moneys = this.repository.findSystemConfiguration();

		String[] listaMonedas = moneys.getAcceptedCurrencies().split(",");
		for (String divisa : listaMonedas)
			if (moneda.getCurrency().equals(divisa))
				return true;

		return false;
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.invoice.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.quantity-no-positive");

		if (!super.getBuffer().getErrors().hasErrors("tax"))
			super.state(object.getTax() > 0, "tax", "sponsor.invoice.form.error.tax-no-positive");

		if (!super.getBuffer().getErrors().hasErrors("registrationTime"))
			super.state(object.getRegistrationTime().after(object.getSponsorship().getMoment()), "registrationTime", "sponsor.invoice.form.error.registration-before-sponsorship");

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 30, ChronoUnit.DAYS);
			super.state(object.getDueDate().after(minimumDeadline), "dueDate", "sponsor.invoice.form.error.too-close-to-registrationTime");
		}
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}
}
