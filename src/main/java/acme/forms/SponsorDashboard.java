
package acme.forms;

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

	public int					numberOfInvoicesTaxLessOrEqual21;

	public int					numberOfSponsorshipsWithLink;

	public Double				averageAmountSponsorships;

	public Double				deviationAmountSponsorships;

	public Double				minimumAmountSponsorships;

	public Double				maximumAmountSponsorships;

	public Double				averageQuantityInvoices;

	public Double				deviationQuantityInvoices;

	public Double				minimumQuantityInvoices;

	public Double				maximumQuantityInvoices;

}
