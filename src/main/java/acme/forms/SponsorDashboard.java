
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

	public double				deviationAmountSponsorships;

	public double				minimumAmountSponsorships;

	public double				maximumAmountSponsorships;

	public Double				averageQuantityInvoices;

	public double				deviationQuantityInvoices;

	public double				minimumQuantityInvoices;

	public double				maximumQuantityInvoices;

}
