package br.ufrgs.inf.dsmoura.repository.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class StringTrimConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent cmp, String value) {
		if (value == null || value.trim().length() == 0) {
			return null;
		}
		else {
			return value;
		}
	}

	public String getAsString(FacesContext context, UIComponent cmp, Object value) {
		if (value != null) {
			return value.toString().trim();
		}
		return null;
	}

}
