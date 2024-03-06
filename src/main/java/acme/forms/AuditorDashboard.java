
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							totalStaticCodeAudits;
	int							totalDynamicCodeAudits;
	Double						averageNumberOfAuditRecords;
	Double						deviationNumberOfAuditRecords;
	Integer						minumumNumberOfAuditRecords;
	Integer						maximumNumberOfAuditRecords;
	Double						averagePeriodLengthOfAuditRecords;
	Double						deviationPeriodLengthOfAuditRecords;
	Double						minumumPeriodLengthOfAuditRecords;
	Double						maximumPeriodLengthOfAuditRecords;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
