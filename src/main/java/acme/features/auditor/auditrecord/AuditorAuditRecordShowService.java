
package acme.features.auditor.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditrecords.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordShowService extends AbstractService<Auditor, AuditRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		CodeAudit codeAudit;

		auditRecordId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditByAuditRecordId(auditRecordId);
		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "finishDate", "mark", "link", "draftMode");

		dataset.put("draftMode", object.getCodeAudit().isDraftMode() && object.isDraftMode());
		dataset.put("masterId", object.getCodeAudit().getId());

		super.getResponse().addData(dataset);
	}

}
