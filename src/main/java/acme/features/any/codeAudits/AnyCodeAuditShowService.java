
package acme.features.any.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.CodeType;
import acme.entities.projects.Project;

@Service
public class AnyCodeAuditShowService extends AbstractService<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AnyCodeAuditRepository repository;

	// AbstractService<Any, CodeAudit> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		CodeAudit codeAudit;
		int masterId;

		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);

		status = super.getRequest().getPrincipal().hasRole(Any.class) && !codeAudit.isDraftMode() && codeAudit != null;

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
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		SelectChoices choicesType;
		choicesType = SelectChoices.from(CodeType.class, object.getType());

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		Collection<String> marks = this.repository.findMarksOfAuditRecordsByCodeAuditId(object.getId());

		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("type", choicesType.getSelected().getKey());
		dataset.put("types", choicesType);
		dataset.put("markMode", CodeAudit.getMarkMode(marks));

		super.getResponse().addData(dataset);

	}

}
