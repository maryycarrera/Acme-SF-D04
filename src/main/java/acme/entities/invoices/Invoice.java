
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.sponsorships.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private Date				registrationTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private Date				finishDate;

	@NotNull
	@Positive
	private Double				quantity;

	@NotNull
	@Min(0)
	private Double				tax;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	private Double				totalAmount;


	public Double getTotalAmount() {
		if (this.tax != null && this.quantity != null)
			return this.tax + this.quantity;
		else
			return null;
	}

	public void setTotalAmount(final Double totalAmount) {
		this.setTotalAmount(totalAmount);
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsorship sponsorship;

};
