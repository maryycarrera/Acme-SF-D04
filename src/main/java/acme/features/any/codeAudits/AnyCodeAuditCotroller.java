
package acme.features.any.codeAudits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.codeaudits.CodeAudit;

@Controller
public class AnyCodeAuditCotroller extends AbstractController<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditShowService	showService;

	@Autowired
	private AnyCodeAuditListService	listAllService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listAllService);

	}

}
