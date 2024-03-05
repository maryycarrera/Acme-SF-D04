
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
	double						avegageNumberOfAuditRecords;
	double						derivationNumberOfAuditRecords;
	int							minumumNumberOfAuditRecords;
	int							maximumNumberOfAuditRecords;
	double						avegagePeriodLengthOfAuditRecords;
	double						derivationPeriodLengthOfAuditRecords;
	double						minumumPeriodLengthOfAuditRecords;
	double						maximumPeriodLengthOfAuditRecords;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
