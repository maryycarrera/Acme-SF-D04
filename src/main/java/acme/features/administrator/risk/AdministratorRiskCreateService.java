//
//package acme.features.administrator.risk;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import acme.client.data.accounts.Administrator;
//import acme.client.services.AbstractService;
//import acme.entities.risks.Risk;
//
//@Service
//public class AdministratorRiskCreateService extends AbstractService<Administrator, Risk> {
//
//	@Autowired
//	private AdministratorRiskRepository riskRepository;
//
//
//	@Override
//	public void authorise() {
//		super.getResponse().setAuthorised(true);
//	}
//
//	@Override
//	public void load() {
//		Risk object;
//
//		object = new Risk();
//
//		super.getBuffer().addData(object);
//	}
//
//	@Override
//	public void bind(final Risk object) {
//		assert object != null;
//
//		super.bind(object, "code", "identificationDate", "impact", "probability", "description", "link", "project");
//	}
//
//	@Override
//	public void validate(final Risk object) {
//		assert object != null;
//
//		if (!super.getBuffer().getErrors().hasErrors("code")) {
//			Risk existing;
//
//			existing = this.riskRepository.findRiskByCode(object.getCode());
//			super.state(existing == null, "reference", "administrator.risk.form.error.code-duplicated");
//		}
//
//		if (!super.getBuffer().getErrors().hasErrors("impact"))
//			super.state(object.getImpact(), null, null, null);
//	}
//
//}
