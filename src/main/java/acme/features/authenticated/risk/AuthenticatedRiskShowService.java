
package acme.features.authenticated.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AuthenticatedRiskShowService extends AbstractService<Authenticated, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedRiskRepository repository;

	// AbstractService<Authenticated, Risk> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int riskId;
		Risk risk;

		riskId = super.getRequest().getData("id", int.class);
		risk = this.repository.findOneRiskById(riskId);

		status = risk != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);

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
		double value = object.value();
		dataset.put("value", value);

		super.getResponse().addData(dataset);
	}

}
