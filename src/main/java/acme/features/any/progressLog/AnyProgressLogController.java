
package acme.features.any.progressLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.progresslogs.ProgressLog;

@Controller
public class AnyProgressLogController extends AbstractController<Any, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProgressLogShowService	showService;

	@Autowired
	private AnyProgressLogListService	listService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}

}
