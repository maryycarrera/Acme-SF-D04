
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------

	// Response attributes ----------------------------------------------------

	public int					numberOfMustUserStories;

	public int					numberOfShouldUserStories;

	public int					numberOfCouldUserStories;

	public int					numberOfWontUserStories;

	public Double				averageCostUserStories;

	public Double				deviationCostUserStories;

	public Double				minimumCostUserStories;

	public Double				maximumCostUserStories;

	public Double				averageCostProjects;

	public Double				deviationCostProjects;

	public Double				minimumCostProjects;

	public Double				maximumCostProjects;

}
