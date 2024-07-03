
package acme.entities.banners;

import java.util.Date;

import javax.persistence.Entity;
// import javax.persistence.Index;
// import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(indexes = {
//	@Index(columnList = "finishDate,startDate")
//})
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
	private Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				finishDate;

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

	// Relationships ----------------------------------------------------------

}
