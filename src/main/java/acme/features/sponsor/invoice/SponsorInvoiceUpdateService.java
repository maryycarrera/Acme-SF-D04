
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Sponsorship sponsorship;

		invoiceId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipByInvoiceId(invoiceId);
		Invoice i = this.repository.findOneInvoiceById(invoiceId);
		status = sponsorship != null && sponsorship.isDraftMode() && i.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		int invoiceId;

		invoiceId = super.getRequest().getData("id", int.class);
		Sponsorship sponsorship = this.repository.findOneSponsorshipByInvoiceId(invoiceId);

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
		object.setSponsorship(sponsorship);
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		Collection<Invoice> invoices;

		invoices = this.repository.findInvoicesFromSponsorshipId(object.getSponsorship().getId());
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "sponsor.invoice.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.quantity-no-positive");

		if (!super.getBuffer().getErrors().hasErrors("registrationTime"))
			super.state(object.getRegistrationTime().after(object.getSponsorship().getMoment()), "registrationTime", "sponsor.invoice.form.error.registration-before-sponsorship");

		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
			if (object.getRegistrationTime() != null) {
				Date minimumDeadline;

				minimumDeadline = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 30, ChronoUnit.DAYS);
				super.state(object.getDueDate().after(minimumDeadline), "dueDate", "sponsor.invoice.form.error.too-close-to-registrationTime");
			}
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");

		if (!super.getBuffer().getErrors().hasErrors()) {
			double valorAnterior = this.repository.findOneInvoiceById(object.getId()).totalAmount();
			double totalActual = object.totalAmount() - valorAnterior;
			for (Invoice i : invoices)
				totalActual += i.totalAmount();
			super.state(totalActual <= object.getSponsorship().getAmount().getAmount(), "*", "sponsor.invoice.form.error.bad-cost");
		}
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
		dataset.put("masterId", object.getSponsorship().getId());

		super.getResponse().addData(dataset);

	}
}
