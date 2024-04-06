
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progresslogs.ProgressLog;
import acme.entities.projects.Project;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findContractsByClientId(int id);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select p from ProgressLog p where p.contract.id = :jobId")
	Collection<ProgressLog> findProgressLogsByContractId(int jobId);

	/**
	 * @Query("select wi.project from WorksIn wi where wi.client.id = :clientId")
	 * Collection<Project> findManyProejctsByClientId(int clientId);
	 */

}
