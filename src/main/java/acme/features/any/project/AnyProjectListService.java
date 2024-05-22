
package acme.features.any.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AnyProjectListService extends AbstractService<Any, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository repository;

	// AbstractService<Any, Project> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Project> object;

		object = this.repository.findPublishedProjects();

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractDescription");
		super.addPayload(dataset, object, "hasFatalErrors", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
