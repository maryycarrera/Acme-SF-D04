
package acme.components;

import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import acme.client.helpers.MessageHelper;

public class LocalisedBooleanFormatter implements Formatter<Boolean> {

	@Override
	public String print(final Boolean object, final Locale locale) {
		assert object != null;
		assert locale != null;

		String result;

		if (locale.getCountry().equals("es"))
			if (object)
				result = "verdadero";
			else
				result = "falso";
		else if (object)
			result = "true";
		else
			result = "false";

		return result;
	}

	@Override
	public Boolean parse(final String text, final Locale locale) throws ParseException {
		assert text != null;
		assert locale != null;

		String errorMessage;

		if (locale.getCountry().equals("es")) {
			if (text.matches("verdadero"))
				return true;
			else if (text.matches("falso"))
				return false;
		} else if (locale.getCountry().equals("en")) {
			if (text.matches("true"))
				return true;
			else if (text.matches("false"))
				return false;
		} else {
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locale);
			throw new ParseException(0, errorMessage);
		}

		return false;
	}

}
