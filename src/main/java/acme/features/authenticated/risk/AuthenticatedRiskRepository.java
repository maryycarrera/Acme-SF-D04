
package acme.features.authenticated.risk;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.risks.Risk;

@Repository
public interface AuthenticatedRiskRepository extends AbstractRepository {

	@Query("SELECT r FROM Risk r")
	Collection<Risk> findAllRisks();

	@Query("SELECT r FROM Risk r WHERE r.id = :id")
	Risk findOneRiskById(int id);

}
