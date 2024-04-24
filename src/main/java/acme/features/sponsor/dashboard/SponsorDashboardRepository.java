
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT count(i) FROM Invoice i WHERE i.tax<=0.21 AND i.sponsorship.sponsor.id = :id")
	int numberOfInvoicesTaxLessOrEqual21(int id);

	@Query("SELECT count(s) FROM Sponsorship s WHERE s.link!=null AND s.sponsor.id = :id")

	int numberOfSponsorshipsWithLink(int id);

	@Query("SELECT avg(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")

	Double averageAmountSponsorships(int id);

	@Query("SELECT stddev(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")

	Double deviationAmountSponsorships(int id);

	@Query("SELECT min(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")

	Double minimumAmountSponsorships(int id);

	@Query("SELECT max(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")

	Double maximumAmountSponsorships(int id);

	@Query("SELECT avg(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")

	Double averageQuantityInvoices(int id);

	@Query("SELECT stddev(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")

	Double deviationQuantityInvoices(int id);

	@Query("SELECT min(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")

	Double minimumQuantityInvoices(int id);

	@Query("SELECT max(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")

	Double maximumQuantityInvoices(int id);

}
