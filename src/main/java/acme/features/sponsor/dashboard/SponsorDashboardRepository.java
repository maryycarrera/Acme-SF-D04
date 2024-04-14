
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT count(i) FROM Invoice i WHERE i.tax<=0.21")
	int numberOfInvoicesTaxLessOrEqual21();

	@Query("SELECT count(s) FROM Sponsorship s WHERE s.link!=null")

	int numberOfSponsorshipsWithLink();

	@Query("SELECT avg(s.amount.amount) FROM Sponsorship s")

	Double averageAmountSponsorships();

	@Query("SELECT stddev(s.amount.amount) FROM Sponsorship s")

	Double deviationAmountSponsorships();

	@Query("SELECT min(s.amount.amount) FROM Sponsorship s")

	Double minimumAmountSponsorships();

	@Query("SELECT max(s.amount.amount) FROM Sponsorship s")

	Double maximumAmountSponsorships();

	@Query("SELECT avg(i.quantity.amount) FROM Invoice i")

	Double averageQuantityInvoices();

	@Query("SELECT stddev(i.quantity.amount) FROM Invoice i")

	Double deviationQuantityInvoices();

	@Query("SELECT min(s.quantity.amount) FROM Invoice i")

	Double minimumQuantityInvoices();

	@Query("SELECT max(s.quantity.amount) FROM Invoice i")

	Double maximumQuantityInvoices();

}
