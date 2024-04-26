
package acme.features.auditor.codeaudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.codeaudits.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditCotroller extends AbstractController<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditShowService		showService;

	@Autowired
	private AuditorCodeAuditCreateService	createService;

	@Autowired
	private AuditorCodeAuditUpdateService	updateService;

	@Autowired
	private AuditorCodeAuditDeleteService	deleteService;

	@Autowired
	private AuditorCodeAuditListService		listAllService;

	@Autowired
	private AuditorCodeAuditPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listAllService);

		super.addCustomCommand("publish", "update", this.publishService);
	}

}
