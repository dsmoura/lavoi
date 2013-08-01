package br.ufrgs.inf.dsmoura.repository.controller.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class NotAfterTodayValidator implements Validator {
   @Override
   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       Date date = (Date) value;
       Date today = new Date();
       if (date.after(today)) {
           String message = "Date must not be later than today.";
           throw new ValidatorException(new FacesMessage(message));
       }
   }

}
