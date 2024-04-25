
package acme.features.client.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.systemconfigurations.SystemConfiguration;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness<25 and p.draftMode=false")
	int progressLogsCompletenessBelow25(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness>=25 and p.completeness<=50 and p.draftMode=false")
	int progressLogsCompletenessBetween25And50(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness>=50 and p.completeness<=75 and p.draftMode=false")
	int progressLogsCompletenessBetween50And75(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness>75 and p.draftMode=false")
	int progressLogsCompletenessAbove75(int clientId);

	@Query("select avg(c.budget.amount) from Contract c where c.client.id = :clientId and c.budget.currency= :currency and c.draftMode=false")
	Double averageBudgetOfContracts(int clientId, String currency);

	@Query("select stddev(c.budget.amount) from Contract c where c.client.id = :clientId and c.budget.currency= :currency and c.draftMode=false")
	Double deviationBudgetOfContracts(int clientId, String currency);

	@Query("select min(c.budget.amount) from Contract c where c.client.id = :clientId and c.budget.currency= :currency and c.draftMode=false")
	Double minimumBudgetOfContracts(int clientId, String currency);

	@Query("select max(c.budget.amount) from Contract c where c.client.id = :clientId and c.budget.currency= :currency and c.draftMode=false")
	Double maximumBudgetOfContracts(int clientId, String currency);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select c.budget from Contract c where c.client.id = :clientId and c.draftMode = false")
	Collection<Money> findBudgetsByClientId(int clientId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();
}
