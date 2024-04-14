
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :id")
	Collection<Invoice> findManyInvoicesBySponsorshipId(int id);

	@Query("SELECT i.sponsorship FROM Invoice i WHERE i.id = :id")
	Sponsorship findOneSponsorshipByInvoiceId(int id);

	@Query("SELECT i FROM Invoice i WHERE i.id = :id")
	Invoice findOneInvoiceById(int id);
}
