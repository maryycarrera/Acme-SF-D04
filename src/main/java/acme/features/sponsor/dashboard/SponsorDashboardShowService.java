
package acme.features.sponsor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemconfigurations.SystemConfiguration;
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
		Collection<Money> amounts;
		Set<String> divisasSet = new HashSet<>();
		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		amounts = this.repository.findAmountsBySponsorId(sponsorId);

		for (Money money : amounts) {
			String currency = money.getCurrency();
			divisasSet.add(currency);
		}

		List<String> divisasList = new ArrayList<>(divisasSet);

		numberOfInvoicesTaxLessOrEqual21 = this.repository.numberOfInvoicesTaxLessOrEqual21(sponsorId);
		numberOfSponsorshipsWithLink = this.repository.numberOfSponsorshipsWithLink(sponsorId);

		Map<String, Double> mediaPorDivisaAMount = new HashMap<>();

		for (String divisa : divisasList) {
			averageAmountSponsorships = this.repository.averageAmountSponsorships(sponsorId, divisa);
			mediaPorDivisaAMount.put(divisa, averageAmountSponsorships);
		}

		Map<String, Double> deviationPorDivisaAmount = new HashMap<>();

		for (String divisa : divisasList) {
			deviationAmountSponsorships = this.repository.deviationAmountSponsorships(sponsorId, divisa);
			deviationPorDivisaAmount.put(divisa, deviationAmountSponsorships);
		}

		Map<String, Double> minPorDivisaAmount = new HashMap<>();

		for (String divisa : divisasList) {
			minimumAmountSponsorships = this.repository.minimumAmountSponsorships(sponsorId, divisa);
			minPorDivisaAmount.put(divisa, minimumAmountSponsorships);
		}

		Map<String, Double> maxPorDivisaAmount = new HashMap<>();

		for (String divisa : divisasList) {
			maximumAmountSponsorships = this.repository.maximumAmountSponsorships(sponsorId, divisa);
			maxPorDivisaAmount.put(divisa, maximumAmountSponsorships);
		}

		////
		Map<String, Double> mediaPorDivisaQuantity = new HashMap<>();

		for (String divisa : divisasList) {
			averageQuantityInvoices = this.repository.averageQuantityInvoices(sponsorId, divisa);
			mediaPorDivisaQuantity.put(divisa, averageQuantityInvoices);
		}

		Map<String, Double> deviationPorDivisaQuantity = new HashMap<>();

		for (String divisa : divisasList) {
			deviationQuantityInvoices = this.repository.deviationQuantityInvoices(sponsorId, divisa);
			deviationPorDivisaQuantity.put(divisa, deviationQuantityInvoices);
		}

		Map<String, Double> minPorDivisaQuantity = new HashMap<>();

		for (String divisa : divisasList) {
			minimumQuantityInvoices = this.repository.minimumQuantityInvoices(sponsorId, divisa);
			minPorDivisaQuantity.put(divisa, minimumQuantityInvoices);
		}

		Map<String, Double> maxPorDivisaQuantity = new HashMap<>();

		for (String divisa : divisasList) {
			maximumQuantityInvoices = this.repository.maximumQuantityInvoices(sponsorId, divisa);
			maxPorDivisaQuantity.put(divisa, maximumQuantityInvoices);
		}

		List<SystemConfiguration> systemConfiguration = this.repository.findSystemConfiguration();

		String[] supportedCurrencies = systemConfiguration.get(0).getAcceptedCurrencies().split(",");

		dashboard = new SponsorDashboard();
		dashboard.setNumberOfInvoicesTaxLessOrEqual21(numberOfInvoicesTaxLessOrEqual21);
		dashboard.setNumberOfSponsorshipsWithLink(numberOfSponsorshipsWithLink);
		dashboard.setAverageAmountSponsorships(mediaPorDivisaAMount);
		dashboard.setDeviationAmountSponsorships(deviationPorDivisaAmount);
		dashboard.setMinimumAmountSponsorships(minPorDivisaAmount);
		dashboard.setMaximumAmountSponsorships(maxPorDivisaAmount);
		dashboard.setAverageQuantityInvoices(mediaPorDivisaQuantity);
		dashboard.setDeviationQuantityInvoices(deviationPorDivisaQuantity);
		dashboard.setMinimumQuantityInvoices(minPorDivisaQuantity);
		dashboard.setMaximumQuantityInvoices(maxPorDivisaQuantity);
		dashboard.setSupportedCurrencies(supportedCurrencies);

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
			"minimumQuantityInvoices", "maximumQuantityInvoices", "supportedCurrencies");

		super.getResponse().addData(dataset);
	}

}
