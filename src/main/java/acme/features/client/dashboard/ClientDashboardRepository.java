
package acme.features.client.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(p) from ProgressLog p where p.completeness<25")
	int progressLogsCompletenessBelow25();

	@Query("select count(p) from ProgressLog p where p.completeness>=25 and p.completeness<=50")
	int progressLogsCompletenessBetween25And50();

	@Query("select count(p) from ProgressLog p where p.completeness>=50 and p.completeness<=75")
	int progressLogsCompletenessBetween50And75();

	@Query("select count(p) from ProgressLog p where p.completeness>75")
	int progressLogsCompletenessAbove75();

	@Query("select avg(c.budget.amount) from Contract c")
	Double averageBudgetOfContracts();

	@Query("select stddev(c.budget.amount) from Contract c")
	Double deviationBudgetOfContracts();

	@Query("select min(c.budget.amount) from Contract c")
	Double minimumBudgetOfContracts();

	@Query("select max(c.budget.amount) from Contract c")
	Double maximumBudgetOfContracts();
}
