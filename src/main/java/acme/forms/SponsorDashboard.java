
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

	public double				averageAmountSponsorship;

	public double				deviationAmountSponsorship;

	public double				minimumAmountSponsorship;

	public double				maximumAmountSponsorship;

	public double				averageQuantitySponsorship;

	public double				deviationQuantitySponsorship;

	public double				minimumQuantitySponsorship;

	public double				maximumQuantitySponsorship;

}
