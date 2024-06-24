/*
 * package acme.features.administrator.risk;
 * 
 * import javax.annotation.PostConstruct;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Controller;
 * 
 * import acme.client.controllers.AbstractController;
 * import acme.client.data.accounts.Administrator;
 * import acme.entities.risks.Risk;
 * 
 * @Controller
 * public class AdministratorRiskController extends AbstractController<Administrator, Risk> {
 * 
 * @Autowired
 * private AdministratorRiskCreateService createService;
 * 
 * @Autowired
 * private AdministratorRiskDeleteService deleteService;
 * 
 * @Autowired
 * private AdministratorRiskListService listService;
 * 
 * @Autowired
 * private AdministratorRiskShowService showService;
 * 
 * @Autowired
 * private AdministratorRiskUpdateService updateService;
 * 
 * 
 * @PostConstruct
 * protected void initialise() {
 * super.addBasicCommand("create", this.createService);
 * super.addBasicCommand("delete", this.deleteService);
 * super.addBasicCommand("list", this.listService);
 * super.addBasicCommand("show", this.showService);
 * super.addBasicCommand("update", this.updateService);
 * }
 * 
 * }
 */
