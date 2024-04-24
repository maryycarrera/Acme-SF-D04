
package acme.features.any.notice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.notices.Notice;

@Controller
public class AnyNoticeController extends AbstractController<Any, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyNoticeListService	listService;

	@Autowired
	private AnyNoticeShowService	showService;

	@Autowired
	private AnyNoticeCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}
}
