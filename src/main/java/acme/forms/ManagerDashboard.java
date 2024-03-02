
package acme.forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.client.data.AbstractForm;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------

	@NotNull
	@Valid
	public Manager				manager;

	// Response attributes ----------------------------------------------------

	public int					numberOfMustUserStories;

	public int					numberOfShouldUserStories;

	public int					numberOfCouldUserStories;

	public int					numberOfWontUserStories;

	public double				averageCostUserStories;

	public double				deviationCostUserStories;

	public double				minimumCostUserStories;

	public double				maximumCostUserStories;

	public double				averageCostProjects;

	public double				deviationCostProjects;

	public double				minimumCostProjects;

	public double				maximumCostProjects;

}
