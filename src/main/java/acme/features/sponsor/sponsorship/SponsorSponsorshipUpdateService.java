
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.TypeSponsorship;
import acme.entities.systemconfigurations.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		super.bind(object, "code", "moment", "startTimeDuration", "finishTimeDuration", "amount", "type", "contact", "link");
		object.setProject(project);

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
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "sponsor.sponsorship.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(object.getAmount().getAmount() >= 0, "amount", "sponsor.sponsorship.form.error.amount-no-positive-or-zero");

		if (!super.getBuffer().getErrors().hasErrors("startTimeDuration"))
			super.state(object.getStartTimeDuration().after(object.getMoment()), "startTimeDuration", "sponsor.sponsorship.form.error.moment-after-start");

		if (!super.getBuffer().getErrors().hasErrors("finishTimeDuration"))
			super.state(object.getFinishTimeDuration().after(object.getStartTimeDuration()), "finishTimeDuration", "sponsor.sponsorship.form.error.start-after-finish");

		if (!super.getBuffer().getErrors().hasErrors("finishTimeDuration")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getStartTimeDuration(), 30, ChronoUnit.DAYS);
			super.state(object.getFinishTimeDuration().after(minimumDeadline), "finishTimeDuration", "sponsor.sponsorship.form.error.too-close-to-startTime");
		}
		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(this.isCurrencyAccepted(object.getAmount()), "amount", "sponsor.sponsorship.form.error.acceptedCurrency");

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		SelectChoices choicesType;
		choicesType = SelectChoices.from(TypeSponsorship.class, object.getType());

		dataset = super.unbind(object, "code", "moment", "startTimeDuration", "finishTimeDuration", "amount", "contact", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("types", choicesType);
		dataset.put("type", choicesType.getSelected().getKey());

		super.getResponse().addData(dataset);

	}

}
