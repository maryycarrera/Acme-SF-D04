
package acme.features.client.progresslog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progresslogs.ProgressLog;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from ProgressLog p where p.contract.id = :masterId")
	Collection<ProgressLog> findManyProgressLogsByMasterId(int masterId);

	@Query("select p.contract from ProgressLog p where p.id = :id")
	Contract findfindOneContractByProgressLogId(int id);

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findOneProgressLogById(int id);
}
