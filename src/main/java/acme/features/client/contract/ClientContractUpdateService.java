
package acme.features.client.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractUpdateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		//TODO: Aquí hay que hacer las reglas de negocio
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "instantiationMoment", "providerName", "customerName", "goals", "budget");
		object.setProject(project);
		;
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
		//TODO: hay que hacerlo para todos los atributos??
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated"); //TODO: lo último lo no sé lo que es
		}

		//if (!super.getBuffer().getErrors().hasErrors("instantiationMoment")) {
		//TODO: establecer unos mínimos y máximos conforme a la fecha
		//Date minimumDeadline;

		//minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS); //esto es para 7 días en el futuro lo dejo comentado por si me sirve luego
		//super.state(MomentHelper.isAfter(object.getDeadline(), minimumDeadline), "deadline", "employer.job.form.error.too-close");
		//}

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(object.getBudget().getAmount() > 0, "salary", "client.contract.form.error.negative-salary"); //TODO: lo último lo no sé lo que es
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		/**
		 * En tu código, el método unbind() se utiliza para transformar un objeto de la
		 * clase Contract en un formato más adecuado para ser mostrado en la interfaz de
		 * usuario, lo cual es necesario cuando se quiere preparar los datos para ser
		 * enviados como respuesta a una solicitud.
		 */
		//TODO: comprobar si hay que hacer algo más
		assert object != null;

		int clientId;
		Dataset dataset;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();

		dataset = super.unbind(object, "instantiationMoment", "providerName", "customerName", "goals", "budget");

		super.getResponse().addData(dataset);
	}

}
