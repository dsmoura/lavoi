package br.ufrgs.inf.dsmoura.repository.controller.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.ufrgs.inf.dsmoura.repository.model.entity.AssetStateType;


public class AssetStateValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if (((AssetStateType) value).getName().equalsIgnoreCase("Certified")) {
				FacesMessage facesMessage = new FacesMessage("The assets cannot be published as certified. The assets have to be certified after the publishment.");
				facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(facesMessage);
			}
		}
	}

}
