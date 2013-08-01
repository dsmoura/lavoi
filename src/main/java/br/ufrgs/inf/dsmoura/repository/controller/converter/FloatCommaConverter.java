package br.ufrgs.inf.dsmoura.repository.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class FloatCommaConverter implements Converter {

  public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String strValue) {
    if ((strValue == null) || (strValue.equals(""))) {
      return null;
    }
    try {
      return Float.valueOf(strValue.replace(',', '.'));
    } catch(RuntimeException e) {
      throw new ConverterException("invalid value: " + strValue);
    }
  }

  public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object objValue) {
    if (objValue == null) {
      return null;
    }
    return objValue.toString().replace('.', ',');
  }
  
}
