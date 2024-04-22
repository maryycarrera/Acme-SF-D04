
package acme.features.client.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


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

		progressLogsCompletenessBelow25 = this.repository.progressLogsCompletenessBelow25(clientId);
		progressLogsCompletenessBetween25And50 = this.repository.progressLogsCompletenessBetween25And50(clientId);
		progressLogsCompletenessBetween50And75 = this.repository.progressLogsCompletenessBetween50And75(clientId);
		progressLogsCompletenessAbove75 = this.repository.progressLogsCompletenessAbove75(clientId);
		averageBudgetOfContracts = this.repository.averageBudgetOfContracts(clientId);
		deviationBudgetOfContracts = this.repository.deviationBudgetOfContracts(clientId);
		minimumBudgetOfContracts = this.repository.minimumBudgetOfContracts(clientId);
		maximumBudgetOfContracts = this.repository.maximumBudgetOfContracts(clientId);

		dashboard = new ClientDashboard();
		dashboard.setProgressLogsCompletenessBelow25(progressLogsCompletenessBelow25);
		dashboard.setProgressLogsCompletenessBetween25And50(progressLogsCompletenessBetween25And50);
		dashboard.setProgressLogsCompletenessBetween50And75(progressLogsCompletenessBetween50And75);
		dashboard.setProgressLogsCompletenessAbove75(progressLogsCompletenessAbove75);
		dashboard.setAverageBudgetOfContracts(averageBudgetOfContracts);
		dashboard.setDeviationBudgetOfContracts(deviationBudgetOfContracts);
		dashboard.setMinimumBudgetOfContracts(minimumBudgetOfContracts);
		dashboard.setMaximumBudgetOfContracts(maximumBudgetOfContracts);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ClientDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"progressLogsCompletenessBelow25", "progressLogsCompletenessBetween25And50", // 
			"progressLogsCompletenessBetween50And75", "progressLogsCompletenessAbove75", //
			"averageBudgetOfContracts", "deviationBudgetOfContracts", "minimumBudgetOfContracts", "maximumBudgetOfContracts");

		super.getResponse().addData(dataset);
	}
}
