
package acme.features.any.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;

@Service
public class AnyContractListService extends AbstractService<Any, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyContractRepository repository;

	// AbstractService<Any, Project> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Contract> object;

		object = this.repository.findPublishedContracts();

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "providerName", "customerName");

		super.getResponse().addData(dataset);
	}
}
