
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progresslogs.ProgressLog;
import acme.entities.projects.Project;
import acme.entities.systemconfigurations.SystemConfiguration;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :clientId and c.project.id = :projectId")
	Collection<Contract> findContractsByClientIdAndProjectId(int clientId, int projectId);

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findContractsByClientId(int clientId);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select c from Contract c where c.code = :code order by c.id")
	Contract findOneContractByCode(String code);

	@Query("select p from ProgressLog p where p.contract.id = :contractId")
	Collection<ProgressLog> findProgressLogsByContractId(int contractId);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findManyContractByProjectId(int projectId);

}
