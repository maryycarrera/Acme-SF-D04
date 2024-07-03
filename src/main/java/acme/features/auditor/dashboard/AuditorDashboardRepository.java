
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.CodeType;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type=:type AND ca.auditor.id=:auditorId")
	int totalCodeAuditsForType(int auditorId, CodeType type);

	@Query("SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.codeAudit.auditor.id=:auditorId")
	int totalAuditRecordsForAuditor(int auditorId);

	@Query("SELECT AVG(SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.codeAudit = ca) FROM CodeAudit ca WHERE ca.auditor.id=:auditorId")
	Double averageNumberOfAuditRecords(int auditorId);

	@Query("SELECT (SELECT COUNT(ar) from AuditRecord ar where ar.codeAudit = ca) FROM CodeAudit ca WHERE ca.auditor.id = :auditorId")
	Collection<Double> auditRecordsForCodeAudit(int auditorId);

	@Query("SELECT MIN(SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.codeAudit = ca) FROM CodeAudit ca WHERE ca.auditor.id=:auditorId")
	Integer minumumNumberOfAuditRecords(int auditorId);

	@Query("SELECT MAX(SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.codeAudit = ca) FROM CodeAudit ca WHERE ca.auditor.id=:auditorId")
	Integer maximumNumberOfAuditRecords(int auditorId);

	@Query("SELECT AVG(TIME_TO_SEC(TIMEDIFF(ar.finishDate, ar.startDate))) FROM AuditRecord ar WHERE ar.codeAudit.auditor.id=:auditorId")
	Double averagePeriodLengthOfAuditRecords(int auditorId);

	@Query("SELECT STDDEV(TIME_TO_SEC(TIMEDIFF(ar.finishDate, ar.startDate))) FROM AuditRecord ar WHERE ar.codeAudit.auditor.id = :auditorId")
	Double deviationPeriodLengthOfAuditRecords(int auditorId);

	@Query("SELECT MIN(TIME_TO_SEC(TIMEDIFF(ar.finishDate, ar.startDate))) FROM AuditRecord ar WHERE ar.codeAudit.auditor.id = :auditorId")
	Double minumumPeriodLengthOfAuditRecords(int auditorId);

	@Query("SELECT MAX(TIME_TO_SEC(TIMEDIFF(ar.finishDate , ar.startDate))) FROM AuditRecord ar WHERE ar.codeAudit.auditor.id = :auditorId")
	Double maximumPeriodLengthOfAuditRecords(int auditorId);
}
