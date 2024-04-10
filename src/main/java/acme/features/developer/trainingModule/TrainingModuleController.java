
package acme.features.developer.trainingModule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingmodules.TrainingModule;
import acme.roles.Developer;

@Controller
public class TrainingModuleController extends AbstractController<Developer, TrainingModule> {

	@Autowired
	private TrainingModuleCreateService		createService;

	@Autowired
	private TrainingModuleDeleteService		deleteService;

	@Autowired
	private TrainingModuleListMineService	listMineService;

	@Autowired
	private TrainingModulePublishService	publishService;

	@Autowired
	private TrainingModuleShowService		showService;

	@Autowired
	private TrainingModuleUpdateService		updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);

	}

}
