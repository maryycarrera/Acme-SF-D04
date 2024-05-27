
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditrecords.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudit c WHERE c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("SELECT c FROM CodeAudit c WHERE c.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditsByAuditorId(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT c FROM CodeAudit c WHERE c.code = :code ORDER BY c.id")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("SELECT a FROM AuditRecord a WHERE a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("SELECT a.mark FROM AuditRecord a WHERE a.codeAudit.id = :codeAuditId")
	Collection<String> findMarksOfAuditRecordsByCodeAuditId(int codeAuditId);
}
