
package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditrecords.AuditRecord;
import acme.entities.codeaudits.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.codeAudit.id = :id")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int id);

	@Query("SELECT ar.codeAudit FROM AuditRecord ar where ar.id = :id")
	CodeAudit findOneCodeAuditByAuditRecordId(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.code = :code ORDER BY ar.id")
	AuditRecord findOneAuditRecordByCode(String code);

	@Query("SELECT ca FROM CodeAudit ca")
	Collection<CodeAudit> findAllCodeAudits();

}
