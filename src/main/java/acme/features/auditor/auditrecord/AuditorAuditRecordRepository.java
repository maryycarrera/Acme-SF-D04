
package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditrecords.AuditRecord;
import acme.entities.codeaudits.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.codeAudit.id = :id")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int id);

	@Query("select ar.codeAudit from AuditRecord ar where ar.id = :id")
	CodeAudit findOneCodeAuditByAuditRecordId(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.code = :code")
	AuditRecord findOneAuditRecordByCode(String code);

}
