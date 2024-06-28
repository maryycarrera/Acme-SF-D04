
package acme.features.authenticated.objective;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.objectives.Objective;

@Controller
public class AuthenticatedObjectiveCotroller extends AbstractController<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedObjectiveShowService	showService;

	@Autowired
	private AuthenticatedObjectiveListService	listAllService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listAllService);
	}

}
