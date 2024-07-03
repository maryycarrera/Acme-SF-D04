
package acme.features.any.auditRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.auditrecords.AuditRecord;

@Controller
public class AnyAuditRecordController extends AbstractController<Any, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditRecordShowService	showService;

	@Autowired
	private AnyAuditRecordListService	listAllService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);

	}

}
