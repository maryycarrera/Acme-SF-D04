
package acme.datatypes;

import java.beans.Transient;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Author {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				username;

	@NotBlank
	private String				surname;

	@NotBlank
	@Email
	private String				name;

	// Derived attributes -----------------------------------------------------


	@Transient
	public String getFullName() {
		StringBuilder result;

		result = new StringBuilder();
		result.append("〈");
		result.append(this.username);
		result.append("〉 - 〈");
		result.append(this.surname);
		result.append(", ");
		result.append(this.name);
		result.append("〉");

		return result.toString();
	}
}
