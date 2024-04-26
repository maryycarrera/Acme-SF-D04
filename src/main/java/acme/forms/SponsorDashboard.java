
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------

	// Response attributes ----------------------------------------------------

	int							numberOfInvoicesTaxLessOrEqual21;

	int							numberOfSponsorshipsWithLink;

	Map<String, Double>			averageAmountSponsorships;

	Map<String, Double>			deviationAmountSponsorships;

	Map<String, Double>			minimumAmountSponsorships;

	Map<String, Double>			maximumAmountSponsorships;

	Map<String, Double>			averageQuantityInvoices;

	Map<String, Double>			deviationQuantityInvoices;

	Map<String, Double>			minimumQuantityInvoices;

	Map<String, Double>			maximumQuantityInvoices;

	String[]					supportedCurrencies;

}
