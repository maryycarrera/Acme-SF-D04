
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

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT c FROM CodeAudit c WHERE c.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeAuditId);
}
