
package acme.entities.notices;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.accounts.UserAccount;
import acme.datatypes.Author;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Notice {

	private final static long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 75)
	private Author				author;

	@NotBlank
	@Length(max = 100)
	private String				message;

	@Email
	private String				email;

	@URL
	private String				link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private UserAccount			userAccount;
}
