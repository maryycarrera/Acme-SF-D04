
package acme.entities.codeaudits;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code")
})
public class CodeAudit extends AbstractEntity {

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
	private Date				executionDate;

	@NotNull
	private CodeType			type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	@URL
	private String				link;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------	


	@Transient
	public static String getMarkMode(final Collection<String> marks) {
		List<String> priorityOrder = List.of("A+", "A", "B", "C", "F", "F-");
		Map<String, Integer> modeMap = new HashMap<>();
		Integer value = 0;
		String mode = "";
		for (String mark : marks)
			if (modeMap.containsKey(mark)) {
				value = modeMap.get(mark);
				value += 1;
				modeMap.put(mark, value);
			} else
				modeMap.put(mark, 1);
		int maxFrequency = 0;
		for (Map.Entry<String, Integer> entry : modeMap.entrySet())
			if (entry.getValue() > maxFrequency) {
				mode = entry.getKey();
				maxFrequency = entry.getValue();
			} else if (entry.getValue() == maxFrequency && priorityOrder.indexOf(mode) < priorityOrder.indexOf(entry.getKey()))
				mode = entry.getKey();
		return mode;
	}
	//	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project	project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor	auditor;
}
