
package acme.entities.banners;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.Administrator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				instantiationMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private LocalDate			startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@PastOrPresent
	private LocalDate			finishDate;

	@NotBlank
	@URL
	private String				picture;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	@NotBlank
	@URL
	private String				targetWebDocument;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Integer displayPeriod() {
		Period p = Period.between(this.startDate, this.finishDate);
		return p.getDays() / 7;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Administrator administrator;
}
