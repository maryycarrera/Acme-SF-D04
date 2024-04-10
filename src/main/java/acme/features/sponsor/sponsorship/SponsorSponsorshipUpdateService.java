
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
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

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		//		if (!super.getBuffer().getErrors().hasErrors("code")) {
		//			Sponsorship existing;
		//
		//			existing = this.repository.findOneSponsorshipByCode(object.getCode());
		//			super.state(existing == null || existing.getId() == object.getId(), "code", "client.contract.form.error.duplicated");
		//		}

		//		if (!super.getBuffer().getErrors().hasErrors("moment"))
		//			super.state(object.getStartTimeDuration().after(object.getMoment()), "moment", "sponsor.sponsorship.form.error.moment-despues-duration");
		//
		//		if (!super.getBuffer().getErrors().hasErrors("finishTimeDuration"))
		//			super.state(object.getFinishTimeDuration().after(object.getStartTimeDuration()), "moment", "sponsor.sponsorship.form.error.start-despues-finish");

		//		if (!super.getBuffer().getErrors().hasErrors("deadline")) {
		//			Date minimumDeadline;
		//
		//			minimumDeadline = MomentHelper.deltaFromMoment(object.getStartTimeDuration(), 30, ChronoUnit.DAYS);
		//			super.state(object.getFinishTimeDuration().after(minimumDeadline), "deadline", "sponsor.sponsorship.form.error.too-close");
		//		}

		//		if (!super.getBuffer().getErrors().hasErrors("amount"))
		//			super.state(object.getAmount().getAmount() > 0, "amount", "sponsor.sponsorship.form.error.amount-no-positive");
		//
		//		if (!super.getBuffer().getErrors().hasErrors("amount"))
		//			super.state(object.getAmount().getAmount() == this.repository.findCostInvoicesFromSponsorshipId(object.getId()), "amount", "sponsor.sponsorship.form.error.amount-distinta-invoices");
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

		dataset = super.unbind(object, "code", "moment", "startTimeDuration", "finishTimeDuration", "amount", "type", "contact", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}

}
