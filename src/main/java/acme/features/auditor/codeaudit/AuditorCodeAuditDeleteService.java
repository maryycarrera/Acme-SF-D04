
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditrecords.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

public class AuditorCodeAuditDeleteService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;
		Auditor auditor;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(codeAuditId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("contractor", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "executionDate", "type", "markMode", "correctiveActions", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findManyAuditRecordsByCodeAuditId(object.getId());
		this.repository.deleteAll(auditRecords);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		int auditorId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "name", object.getProject());

		dataset = super.unbind(object, "code", "executionDate", "type", "markMode", "correctiveActions", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}
}
