package br.ufrgs.inf.dsmoura.repository.controller.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Email Valitator JSF.
 * @author dionatan-moura
 */
public class EmailValidator implements Validator {

	private final String EMAIL_REGEXP = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";

	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
		String emailStr = (String) value;
		if ((emailStr == null) || (emailStr.length() == 0)) {
			return;
		}
		Pattern mask = Pattern.compile(EMAIL_REGEXP);
		Matcher matcher = mask.matcher( emailStr );
		if (!matcher.matches()) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Invalid email.");
			message.setSummary("Invalid email.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

}