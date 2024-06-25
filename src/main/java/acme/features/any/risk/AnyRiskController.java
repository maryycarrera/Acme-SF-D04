
package acme.features.any.risk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.risks.Risk;

@Controller
public class AnyRiskController extends AbstractController<Any, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyRiskListService	listService;

	@Autowired
	private AnyRiskShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
