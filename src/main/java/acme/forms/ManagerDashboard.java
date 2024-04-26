
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

	int							numberOfMustUserStories;

	int							numberOfShouldUserStories;

	int							numberOfCouldUserStories;

	int							numberOfWontUserStories;

	Double						averageCostUserStories;

	Double						deviationCostUserStories;

	Double						minimumCostUserStories;

	Double						maximumCostUserStories;

	Double						averageCostProjects;

	Double						deviationCostProjects;

	Double						minimumCostProjects;

	Double						maximumCostProjects;

}
