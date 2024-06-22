
package acme.features.any.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AnyRiskShowService extends AbstractService<Any, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyRiskRepository repository;

	// AbstractService<Any, Risk> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int riskId;
		Risk risk;

		riskId = super.getRequest().getData("id", int.class);
		risk = this.repository.findOneRiskById(riskId);

		status = risk != null && super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Risk object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneRiskById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "identificationDate", "description", "link");
		Double value = object.value();
		dataset.put("value", value);

		super.getResponse().addData(dataset);
	}

}
