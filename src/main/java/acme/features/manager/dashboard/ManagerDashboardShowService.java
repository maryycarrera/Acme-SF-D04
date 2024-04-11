
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstories.Priority;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		int numberOfMustUserStories;
		int numberOfShouldUserStories;
		int numberOfCouldUserStories;
		int numberOfWontUserStories;
		Double averageCostUserStories;
		Double deviationCostUserStories;
		Double minimumCostUserStories;
		Double maximumCostUserStories;
		Double averageCostProjects;
		Double deviationCostProjects;
		Double minimumCostProjects;
		Double maximumCostProjects;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		numberOfMustUserStories = this.repository.numberOfPriorityUserStories(Priority.MUST, managerId);
		numberOfShouldUserStories = this.repository.numberOfPriorityUserStories(Priority.SHOULD, managerId);
		numberOfCouldUserStories = this.repository.numberOfPriorityUserStories(Priority.COULD, managerId);
		numberOfWontUserStories = this.repository.numberOfPriorityUserStories(Priority.WONT, managerId);

		averageCostUserStories = this.repository.averageCostUserStories(managerId);
		deviationCostUserStories = this.repository.deviationCostUserStories(managerId);
		minimumCostUserStories = this.repository.minimumCostUserStories(managerId);
		maximumCostUserStories = this.repository.maximumCostUserStories(managerId);

		averageCostProjects = this.repository.averageCostProjects(managerId);
		deviationCostProjects = this.repository.deviationCostProjects(managerId);
		minimumCostProjects = this.repository.minimumCostProjects(managerId);
		maximumCostProjects = this.repository.maximumCostProjects(managerId);

		dashboard = new ManagerDashboard();
		dashboard.setNumberOfMustUserStories(numberOfMustUserStories);
		dashboard.setNumberOfShouldUserStories(numberOfShouldUserStories);
		dashboard.setNumberOfCouldUserStories(numberOfCouldUserStories);
		dashboard.setNumberOfWontUserStories(numberOfWontUserStories);
		dashboard.setAverageCostUserStories(averageCostUserStories);
		dashboard.setDeviationCostUserStories(deviationCostUserStories);
		dashboard.setMinimumCostUserStories(minimumCostUserStories);
		dashboard.setMaximumCostUserStories(maximumCostUserStories);
		dashboard.setAverageCostProjects(averageCostProjects);
		dashboard.setDeviationCostProjects(deviationCostProjects);
		dashboard.setMinimumCostProjects(minimumCostProjects);
		dashboard.setMaximumCostProjects(maximumCostProjects);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"numberOfMustUserStories", //
			"numberOfShouldUserStories", // 
			"numberOfCouldUserStories", //
			"numberOfWontUserStories", //
			"averageCostUserStories", //
			"deviationCostUserStories", //
			"minimumCostUserStories", //
			"maximumCostUserStories", //
			"averageCostProjects", //
			"deviationCostProjects", //
			"minimumCostProjects", //
			"maximumCostProjects");

		super.getResponse().addData(dataset);
	}

}
