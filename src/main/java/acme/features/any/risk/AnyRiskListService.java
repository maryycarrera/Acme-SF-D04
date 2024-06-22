
package acme.features.any.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AnyRiskListService extends AbstractService<Any, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyRiskRepository repository;

	// AbstractService<Any, Risk> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Risk> object;

		object = this.repository.findAllRisks();

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "identificationDate");
		Double value = object.value();
		dataset.put("value", value);
		super.addPayload(dataset, object, "impact", "probability", "link", "description");

		super.getResponse().addData(dataset);
	}

}
