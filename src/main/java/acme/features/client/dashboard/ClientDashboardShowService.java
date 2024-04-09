
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
		ClientDashboard dashboard;
		int progressLogsCompletenessBelow25;
		int progressLogsCompletenessBetween25And50;
		int progressLogsCompletenessBetween50And75;
		int progressLogsCompletenessAbove75;
		Double averageBudgetOfContracts;
		Double deviationBudgetOfContracts;
		Double minimumBudgetOfContracts;
		Double maximumBudgetOfContracts;

		progressLogsCompletenessBelow25 = this.repository.progressLogsCompletenessBelow25();
		progressLogsCompletenessBetween25And50 = this.repository.progressLogsCompletenessBetween25And50();
		progressLogsCompletenessBetween50And75 = this.repository.progressLogsCompletenessBetween50And75();
		progressLogsCompletenessAbove75 = this.repository.progressLogsCompletenessAbove75();
		averageBudgetOfContracts = this.repository.averageBudgetOfContracts();
		deviationBudgetOfContracts = this.repository.deviationBudgetOfContracts();
		minimumBudgetOfContracts = this.repository.minimumBudgetOfContracts();
		maximumBudgetOfContracts = this.repository.maximumBudgetOfContracts();

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
