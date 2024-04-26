
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemconfigurations.SystemConfiguration;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT count(i) FROM Invoice i WHERE i.tax<=0.21 AND i.sponsorship.sponsor.id = :id")
	int numberOfInvoicesTaxLessOrEqual21(int id);

	@Query("SELECT count(s) FROM Sponsorship s WHERE s.link!=null AND s.sponsor.id = :id")

	int numberOfSponsorshipsWithLink(int id);

	@Query("SELECT avg(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id AND s.amount.currency= :currency")

	Double averageAmountSponsorships(int id, String currency);

	@Query("SELECT stddev(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id AND s.amount.currency= :currency")

	Double deviationAmountSponsorships(int id, String currency);

	@Query("SELECT min(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id AND s.amount.currency= :currency")

	Double minimumAmountSponsorships(int id, String currency);

	@Query("SELECT max(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id AND s.amount.currency= :currency")

	Double maximumAmountSponsorships(int id, String currency);

	@Query("SELECT avg(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id AND i.quantity.currency= :currency")

	Double averageQuantityInvoices(int id, String currency);

	@Query("SELECT stddev(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id AND i.quantity.currency= :currency")

	Double deviationQuantityInvoices(int id, String currency);

	@Query("SELECT min(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id AND i.quantity.currency= :currency")

	Double minimumQuantityInvoices(int id, String currency);

	@Query("SELECT max(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id AND i.quantity.currency= :currency")

	Double maximumQuantityInvoices(int id, String currency);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select s.amount from Sponsorship s where s.sponsor.id = :sponsorId")
	Collection<Money> findAmountsBySponsorId(int sponsorId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
