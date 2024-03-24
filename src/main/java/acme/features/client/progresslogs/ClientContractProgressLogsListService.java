
package acme.features.client.progresslogs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.progresslogs.ProgressLog;
import acme.roles.Client;

@Service
public class ClientContractProgressLogsListService extends AbstractService<Client, ProgressLog> {
	//TODO: preguntar si esto es así porque ProgressLog sale de Contract

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractProgressLogsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);
		status = contract != null && super.getRequest().getPrincipal().hasRole(contract.getClient());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<ProgressLog> objects;
		int masterId;

		masterId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyProgressLogsByMasterId(masterId);

		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "responsiblePerson", "completeness");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<ProgressLog> objects) {
		assert objects != null;

		int masterId;
		Contract contract;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);
		showCreate = super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
