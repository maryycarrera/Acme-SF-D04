
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

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
	Double						averageValueInRisks;
	Double						deviationValueInRisks;
	Integer						minumumValueInRisks;
	Integer						maximumValueInRisks;
	Double						averageNumberOfClaimPostedOver10weeks;
	Double						deviationNumberOfClaimPostedOver10weeks;
	Integer						minumumNumberOfClaimPostedOver10weeks;
	Integer						maximumNumberOfClaimPostedOver10weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
