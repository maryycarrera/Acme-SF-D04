
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeaudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CodeAudit> codeAudits;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		codeAudits = this.repository.findCodeAuditsByAuditorId(id);

		super.getBuffer().addData(codeAudits);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		Collection<String> marks = this.repository.findMarksOfAuditRecordsByCodeAuditId(object.getId());
		dataset = super.unbind(object, "code", "executionDate", "type", "draftMode");
		dataset.put("markMode", CodeAudit.getMarkMode(marks));

		super.addPayload(dataset, object, "correctiveActions", "link", "project.code");
		super.getResponse().addData(dataset);
	}

}
