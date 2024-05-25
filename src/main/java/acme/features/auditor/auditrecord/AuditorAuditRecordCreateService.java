
package acme.features.auditor.auditrecord;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.auditrecords.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findOneCodeAuditById(masterId);
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int masterId;
		CodeAudit codeAudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findOneCodeAuditById(masterId);

		object = new AuditRecord();
		object.setDraftMode(true);
		object.setCodeAudit(codeAudit);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "startDate", "finishDate", "mark", "link");

	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;

			existing = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(existing == null, "code", "auditor.audit-record.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getFinishDate()) && MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), 1, ChronoUnit.HOURS), "finishDate", "auditor.audit-record.form.error.low-period");
		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isAfter(object.getStartDate(), object.getCodeAudit().getExecutionDate()), "startDate", "auditor.audit-record.form.error.code-audit-time");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "finishDate", "mark", "link");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("draftMode", object.getCodeAudit().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
