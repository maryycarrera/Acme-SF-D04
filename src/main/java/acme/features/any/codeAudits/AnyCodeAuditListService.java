
package acme.features.any.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeaudits.CodeAudit;

@Service
public class AnyCodeAuditListService extends AbstractService<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AnyCodeAuditRepository repository;

	// AbstractService<Any, CodeAudit> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CodeAudit> codeAudits;

		codeAudits = this.repository.findPublishedCodeAudit();

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
