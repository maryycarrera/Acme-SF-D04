
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claims.Claim;

@Service
public class AnyClaimCreateService extends AbstractService<Any, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyClaimRepository claimRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim claim = new Claim();

		super.getBuffer().addData(claim);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean duplicatedCode = false;
			for (Claim claim : this.claimRepository.findAllClaims())
				if (claim.getCode().equals(object.getCode())) {
					duplicatedCode = true;
					break;
				}
			super.state(!duplicatedCode, "code", "manager.project.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("confirm")) {
			final boolean confirm = super.getRequest().getData("confirm", boolean.class);

			super.state(confirm, "confirm", "any.claim.form.error.not-confirmed");
		}
	}

	@Override
	public void perform(final Claim object) {
		assert object != null;

		this.claimRepository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
		dataset.put("confirm", false);

		super.getResponse().addData(dataset);
	}

}
