
package acme.features.client.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness<25")
	int progressLogsCompletenessBelow25(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness>=25 and p.completeness<=50")
	int progressLogsCompletenessBetween25And50(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness>=50 and p.completeness<=75")
	int progressLogsCompletenessBetween50And75(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness>75")
	int progressLogsCompletenessAbove75(int clientId);

	@Query("select avg(c.budget.amount) from Contract c where c.client.id = :clientId")
	Double averageBudgetOfContracts(int clientId);

	@Query("select stddev(c.budget.amount) from Contract c where c.client.id = :clientId")
	Double deviationBudgetOfContracts(int clientId);

	@Query("select min(c.budget.amount) from Contract c where c.client.id = :clientId")
	Double minimumBudgetOfContracts(int clientId);

	@Query("select max(c.budget.amount) from Contract c where c.client.id = :clientId")
	Double maximumBudgetOfContracts(int clientId);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);
}
