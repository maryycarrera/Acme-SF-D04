
package acme.features.any.codeAudits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.projects.Project;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudit c WHERE c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("SELECT a.mark FROM AuditRecord a WHERE a.codeAudit.id = :codeAuditId")
	Collection<String> findMarksOfAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("SELECT c FROM CodeAudit c WHERE c.draftMode = false")
	Collection<CodeAudit> findPublishedCodeAudit();
}
