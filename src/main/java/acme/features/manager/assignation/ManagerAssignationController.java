
package acme.features.manager.assignation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.assignations.Assignation;
import acme.roles.Manager;

@Controller
public class ManagerAssignationController extends AbstractController<Manager, Assignation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAssignationListService	listService;

	@Autowired
	private ManagerAssignationShowService	showService;

	@Autowired
	private ManagerAssignationCreateService	createService;

	@Autowired
	private ManagerAssignationDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);

	}

}
