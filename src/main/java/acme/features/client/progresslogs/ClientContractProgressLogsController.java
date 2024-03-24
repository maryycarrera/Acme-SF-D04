
package acme.features.client.progresslogs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.progresslogs.ProgressLog;
import acme.roles.Client;

@Controller
public class ClientContractProgressLogsController extends AbstractController<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractProgressLogsListService listService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
	}
}
