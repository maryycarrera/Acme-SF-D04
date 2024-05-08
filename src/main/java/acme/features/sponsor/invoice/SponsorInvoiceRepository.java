
package acme.features.sponsor.invoice;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemconfigurations.SystemConfiguration;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("SELECT i FROM Invoice i WHERE i.code = :code")
	Invoice findOneInvoiceByCode(String code);

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :id")
	Collection<Invoice> findManyInvoicesBySponsorshipId(int id);

	@Query("SELECT i.sponsorship FROM Invoice i WHERE i.id = :id")
	Sponsorship findOneSponsorshipByInvoiceId(int id);

	@Query("SELECT i FROM Invoice i WHERE i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :id")
	List<Invoice> findInvoicesFromSponsorshipId(int id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
