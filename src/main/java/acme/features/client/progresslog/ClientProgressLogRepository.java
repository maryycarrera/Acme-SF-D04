
package acme.features.client.progresslog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progresslogs.ProgressLog;
import acme.roles.Client;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from ProgressLog p where p.contract.id = :masterId")
	Collection<ProgressLog> findManyProgressLogsByMasterId(int masterId);

	@Query("select p.contract from ProgressLog p where p.id = :id")
	Contract findOneContractByProgressLogId(int id);

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select p from ProgressLog p where p.recordId = :recordId")
	ProgressLog findOneProgressLogRecordId(String recordId);

	@Query("select pl from ProgressLog pl where pl.recordId = :recordId")
	ProgressLog findOneProgressLogByRecordId(String recordId);

	@Query("select cl from Client cl where cl.id = :clientId")
	Client findOneClientById(int clientId);

	@Query("select max(pg.completeness) from ProgressLog pg where pg.contract.id = :contractId and pg.draftMode = false")
	double findPublishedProgressLogWithMaxCompletenessPublished(int contractId);

}
