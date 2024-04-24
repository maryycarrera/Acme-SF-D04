
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorDashboard dashboard;
		int numberOfInvoicesTaxLessOrEqual21;
		int numberOfSponsorshipsWithLink;
		Double averageAmountSponsorships;
		Double deviationAmountSponsorships;
		Double minimumAmountSponsorships;
		Double maximumAmountSponsorships;
		Double averageQuantityInvoices;
		Double deviationQuantityInvoices;
		Double minimumQuantityInvoices;
		Double maximumQuantityInvoices;

		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		numberOfInvoicesTaxLessOrEqual21 = this.repository.numberOfInvoicesTaxLessOrEqual21(sponsorId);
		numberOfSponsorshipsWithLink = this.repository.numberOfSponsorshipsWithLink(sponsorId);
		averageAmountSponsorships = this.repository.averageAmountSponsorships(sponsorId);
		deviationAmountSponsorships = this.repository.deviationAmountSponsorships(sponsorId);
		minimumAmountSponsorships = this.repository.minimumAmountSponsorships(sponsorId);
		maximumAmountSponsorships = this.repository.maximumAmountSponsorships(sponsorId);
		averageQuantityInvoices = this.repository.averageQuantityInvoices(sponsorId);
		deviationQuantityInvoices = this.repository.deviationQuantityInvoices(sponsorId);
		minimumQuantityInvoices = this.repository.minimumQuantityInvoices(sponsorId);
		maximumQuantityInvoices = this.repository.maximumQuantityInvoices(sponsorId);

		dashboard = new SponsorDashboard();
		dashboard.setNumberOfInvoicesTaxLessOrEqual21(numberOfInvoicesTaxLessOrEqual21);
		dashboard.setNumberOfSponsorshipsWithLink(numberOfSponsorshipsWithLink);
		dashboard.setAverageAmountSponsorships(averageAmountSponsorships);
		dashboard.setDeviationAmountSponsorships(deviationAmountSponsorships);
		dashboard.setMinimumAmountSponsorships(minimumAmountSponsorships);
		dashboard.setMaximumAmountSponsorships(maximumAmountSponsorships);
		dashboard.setAverageQuantityInvoices(averageQuantityInvoices);
		dashboard.setDeviationQuantityInvoices(deviationQuantityInvoices);
		dashboard.setMinimumQuantityInvoices(minimumQuantityInvoices);
		dashboard.setMaximumQuantityInvoices(maximumQuantityInvoices);

		super.getBuffer().addData(dashboard);

	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"numberOfInvoicesTaxLessOrEqual21", "numberOfSponsorshipsWithLink",//
			"averageAmountSponsorships", "deviationAmountSponsorships", //
			"minimumAmountSponsorships", "maximumAmountSponsorships", //
			"averageQuantityInvoices", "deviationQuantityInvoices", //
			"minimumQuantityInvoices", "maximumQuantityInvoices");

		super.getResponse().addData(dataset);
	}

}
