package br.ufrgs.inf.dsmoura.repository.controller.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.ufrgs.inf.dsmoura.repository.model.dao.GenericDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;


public class ApplicationDomainConverter implements Converter {
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		ApplicationDomain entity = new ApplicationDomain();
		entity.setApplicationdomainPk(Integer.valueOf(value));
		return GenericDAO.getInstance().findUnique(ApplicationDomain.class, entity.getApplicationdomainPk());
	}

	public String getAsString(FacesContext ctx, UIComponent component, Object value) {
		if (value != null) {
			return ((ApplicationDomain) value).getApplicationdomainPk().toString();
		}
		return null;
	}
}
