
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
		super.getResponse().setAuthorised(true);
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

		numberOfInvoicesTaxLessOrEqual21 = this.repository.numberOfInvoicesTaxLessOrEqual21();
		numberOfSponsorshipsWithLink = this.repository.numberOfSponsorshipsWithLink();
		averageAmountSponsorships = this.repository.averageAmountSponsorships();
		deviationAmountSponsorships = this.repository.deviationAmountSponsorships();
		minimumAmountSponsorships = this.repository.minimumAmountSponsorships();
		maximumAmountSponsorships = this.repository.maximumAmountSponsorships();
		averageQuantityInvoices = this.repository.averageQuantityInvoices();
		deviationQuantityInvoices = this.repository.deviationQuantityInvoices();
		minimumQuantityInvoices = this.repository.minimumQuantityInvoices();
		maximumQuantityInvoices = this.repository.maximumQuantityInvoices();

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
