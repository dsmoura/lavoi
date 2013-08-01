package br.ufrgs.inf.dsmoura.repository.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.ufrgs.inf.dsmoura.repository.controller.solr.SearchOrder;

public class SearchOrderConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			return SearchOrder.valueOf(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof SearchOrder) {
			return ((SearchOrder) value).name();
		}
		return null;
	}

}
