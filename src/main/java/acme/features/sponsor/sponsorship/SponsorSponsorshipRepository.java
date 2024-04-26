
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id = :id")
	Collection<Sponsorship> findSponsorshipBySponsorId(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("SELECT s FROM Sponsor s WHERE s.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("SELECT s FROM Sponsorship s WHERE s.code = :code")
	Sponsorship findOneSponsorshipByCode(String code);

	//Duda, se puede utilizar una derivada aqui?
	@Query("SELECT sum(i.quantity*i.tax) FROM Invoice i WHERE i.sponsorship= :id")
	Double findCostInvoicesFromSponsorshipId(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :id")
	Collection<Invoice> findInvoicesBySponsorshipId(int id);

}
