
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministartorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfAuditor;
	int							numberOfClient;
	int							numberOfConsumer;
	int							numberOfDeveloper;
	int							numberOfManager;
	int							numberOfProvider;
	int							numberOfSponsor;
	Double						ratioOfNoticesWithEmailAndLink;
	Double						ratioCriticalObjectives;
	Double						ratioNonCriticalObjectives;
	double						averageValueInRisks;
	double						deviationValueInRisks;
	int							minumumValueInRisks;
	int							maximumValueInRisks;
	double						averageNumberOfClaimPostedOver10weeks;
	double						deviationNumberOfClaimPostedOver10weeks;
	int							minumumNumberOfClaimPostedOver10weeks;
	int							maximumNumberOfClaimPostedOver10weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
