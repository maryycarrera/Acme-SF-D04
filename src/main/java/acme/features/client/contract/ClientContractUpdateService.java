
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.entities.systemconfigurations.SystemConfiguration;
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

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(masterId);
		status = contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(contract.getClient());

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
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		object.setProject(project);
		;
	}

	public boolean isCurrencyAccepted(final Money moneda) {
		SystemConfiguration moneys;
		moneys = this.repository.findSystemConfiguration();

		String[] listaMonedas = moneys.getAcceptedCurrencies().split(",");
		for (String divisa : listaMonedas)
			if (moneda.getCurrency().equals(divisa))
				return true;

		return false;
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "client.contract.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			super.state(object.getProject() != null, "budget", "client.contract.form.error.no-budget");
			if (object.getProject() != null) {
				double allBudgets = 0.0;
				Collection<Contract> contracts;

				contracts = this.repository.findContractsByClientIdAndProjectId(object.getClient().getId(), object.getProject().getId());
				for (Contract c : contracts)
					allBudgets += c.getBudget().getAmount();

				allBudgets += object.getBudget().getAmount();

				super.state(allBudgets <= object.getProject().getCost(), "*", "client.contract.form.error.excededBudget");
				super.state(object.getBudget().getAmount() >= 0, "budget", "client.contract.form.error.negative-budget");
				super.state(object.getBudget().getAmount() <= object.getProject().getCost(), "budget", "client.contract.form.error.bugdet-major-project-cost");
				super.state(this.isCurrencyAccepted(object.getBudget()), "budget", "client.contract.form.error.acceptedCurrency");

			}
		}

	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}

}
