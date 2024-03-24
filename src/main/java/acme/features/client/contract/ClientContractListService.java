
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.roles.Client;

@Service
public class ClientContractListService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int clientId;
		Collection<Contract> contracts;

		clientId = super.getRequest().getData("id", int.class);
		contracts = this.repository.findContractsByClientId(clientId);
		status = contracts != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Contract> objects;
		int clientId;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findContractsByClientId(clientId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Contract object) {
		//TODO: comprobar si hay que formatear los datos antes de meterlos en el dataset
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "providerName", "customerName");

		super.getResponse().addData(dataset);
	}
}
