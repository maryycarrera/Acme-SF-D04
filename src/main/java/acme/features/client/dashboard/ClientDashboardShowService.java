
package acme.features.client.dashboard;

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
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	@Autowired
	private ClientDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		int clientId = super.getRequest().getPrincipal().getActiveRoleId();

		ClientDashboard dashboard;
		int progressLogsCompletenessBelow25;
		int progressLogsCompletenessBetween25And50;
		int progressLogsCompletenessBetween50And75;
		int progressLogsCompletenessAbove75;
		Double averageBudgetOfContracts;
		Double deviationBudgetOfContracts;
		Double minimumBudgetOfContracts;
		Double maximumBudgetOfContracts;
		Collection<Money> budgets;
		Set<String> divisasSet = new HashSet<>();

		dashboard = new ClientDashboard();

		budgets = this.repository.findBudgetsByClientId(clientId);

		for (Money money : budgets) {
			String currency = money.getCurrency();
			divisasSet.add(currency);
		}

		List<String> divisasList = new ArrayList<>(divisasSet);

		Map<String, Double> mediaPorDivisa = new HashMap<>();

		for (String divisa : divisasList) {
			averageBudgetOfContracts = this.repository.averageBudgetOfContracts(clientId, divisa);
			mediaPorDivisa.put(divisa, averageBudgetOfContracts);
		}

		Map<String, Double> deviationPorCurrency = new HashMap<>();

		for (String divisa : divisasList) {
			deviationBudgetOfContracts = this.repository.deviationBudgetOfContracts(clientId, divisa);
			deviationPorCurrency.put(divisa, deviationBudgetOfContracts);
		}

		Map<String, Double> maximumPorCurrency = new HashMap<>();

		for (String divisa : divisasList) {
			maximumBudgetOfContracts = this.repository.maximumBudgetOfContracts(clientId, divisa);
			maximumPorCurrency.put(divisa, maximumBudgetOfContracts);
		}

		Map<String, Double> minimumPorCurrency = new HashMap<>();

		for (String divisa : divisasList) {
			minimumBudgetOfContracts = this.repository.minimumBudgetOfContracts(clientId, divisa);
			minimumPorCurrency.put(divisa, minimumBudgetOfContracts);
		}

		List<SystemConfiguration> systemConfiguration = this.repository.findSystemConfiguration();

		String[] supportedCurrencies = systemConfiguration.get(0).getAcceptedCurrencies().split(",");

		progressLogsCompletenessBelow25 = this.repository.progressLogsCompletenessBelow25(clientId);
		progressLogsCompletenessBetween25And50 = this.repository.progressLogsCompletenessBetween25And50(clientId);
		progressLogsCompletenessBetween50And75 = this.repository.progressLogsCompletenessBetween50And75(clientId);
		progressLogsCompletenessAbove75 = this.repository.progressLogsCompletenessAbove75(clientId);

		dashboard.setProgressLogsCompletenessBelow25(progressLogsCompletenessBelow25);
		dashboard.setProgressLogsCompletenessBetween25And50(progressLogsCompletenessBetween25And50);
		dashboard.setProgressLogsCompletenessBetween50And75(progressLogsCompletenessBetween50And75);
		dashboard.setProgressLogsCompletenessAbove75(progressLogsCompletenessAbove75);
		dashboard.setAverageBudgetOfContracts(mediaPorDivisa);
		dashboard.setDeviationBudgetOfContracts(deviationPorCurrency);
		dashboard.setMinimumBudgetOfContracts(minimumPorCurrency);
		dashboard.setMaximumBudgetOfContracts(maximumPorCurrency);

		dashboard.setSupportedCurrencies(supportedCurrencies);
		super.getBuffer().addData(dashboard);

	}

	@Override
	public void unbind(final ClientDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "progressLogsCompletenessBelow25", "progressLogsCompletenessBetween25And50", "progressLogsCompletenessBetween50And75", "progressLogsCompletenessAbove75", "averageBudgetOfContracts", "deviationBudgetOfContracts",
			"minimumBudgetOfContracts", "maximumBudgetOfContracts", "supportedCurrencies");

		super.getResponse().addData(dataset);

	}

}
