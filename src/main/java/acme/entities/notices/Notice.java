
package acme.entities.notices;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Notice extends AbstractEntity {

	private final static long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				message;

	@Email
	private String				email;

	@URL
	private String				link;

	@NotBlank
	private String				username;

	@NotBlank
	@Pattern(regexp = "^.+, .+$")
	private String				fullName;


	@Transient
	public String author() {
		StringBuilder author;

		author = new StringBuilder();
		String separator = " - ";
		author.append(this.username);
		author.append(separator);
		author.append(this.fullName);

		return author.toString();
	}

}
