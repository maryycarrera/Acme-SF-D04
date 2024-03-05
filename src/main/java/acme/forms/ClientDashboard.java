
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	private final static long	serialVersionUID	= 1L;

	int							progressLogsCompletenessBelow25;
	int							progressLogsCompletenessBetween25And50;
	int							progressLogsCompletenessBetween50And75;
	int							progressLogsCompletenessAbove75;
	double						averageBudgetOfContracts;
	double						deviationBudgetOfContracts;
	double						minimumBudgetOfContracts;
	double						maximumBudgetOfContracts;
}
