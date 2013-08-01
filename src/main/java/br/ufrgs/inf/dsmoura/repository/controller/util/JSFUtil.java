package br.ufrgs.inf.dsmoura.repository.controller.util;

import java.io.IOException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.exception.UserNotLoggedException;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;

public class JSFUtil {
	
	final static Log logger = LogFactory.getLog(JSFUtil.class);
	
	public static String getRequestParameter(String name) {
		String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
		if (param == null) {
			logger.error("request parameter is null: " + name);
		}
		else if (param.length() == 0) {
			logger.error("request parameter is empty: " + name);
		}
		return param;
	}
	
	public static Object getSessionParameter(String name) {
		Object param = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
		if (param == null) {
			logger.debug("session parameter is null: " + name);
		}
		return param;
	}
	
	public static String getRequestBaseURL() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			StringBuffer requestURL = ((HttpServletRequest) request).getRequestURL();
			int lastSlashIndex = requestURL.lastIndexOf("/");
			return requestURL.substring(0, lastSlashIndex);
		} else {
			return "";
		}
	}
	
	public static void addErrorMessage(String message) {
		addErrorMessage(null, message);
	}
	
	public static void addErrorMessage(String field, String message) {
		FacesMessage facesMessage = new FacesMessage(message);
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(field, facesMessage);
	}
	
	public static List<SelectItem> toSelectItem(List<?> eds, String property) {
		return toSelectItem(eds, property, true, false);
	}
	
	public static <T> List<SelectItem> toSelectItem(List<T> eds, String property, boolean firstSelectWord, boolean firstBlank) {
		if (firstSelectWord && firstBlank) {
			throw new IllegalArgumentException("The list cannot contains first select word and first blank.");
		}
		List<SelectItem> list = new ArrayList<SelectItem>();
		if (firstSelectWord) {
			list.add(new SelectItem(null, "Select..."));
		} else if (firstBlank) {
			list.add(new SelectItem(null, ""));
		}
		for (T ed : eds) {
			list.add(new SelectItem(ed, getPropertyValue(ed, property)));
		}
		return list;
	}
	
	public static List<SelectItem> toSelectItem(List<String> itemList) {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (String item : itemList) {
			list.add(new SelectItem(item, item));
		}
		return list;
	}
	
	public static void downloadFile(String filename, byte[] binaryFile, String contentType) {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setHeader("Content-disposition", "attachment;filename=\"" + filename + "\"");
		response.setContentLength(binaryFile.length);
		response.setContentType(contentType);
		try {
			response.getOutputStream().write(binaryFile);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getPropertyValue(Object o, String name) {
		String camelCaseMethodName = (new StringBuilder("get"))
													.append(name.substring(0, 1).toUpperCase())
													.append(name.substring(1)).toString();
		try {
			Method m = o.getClass().getMethod(camelCaseMethodName, new Class[0]);
			return m.invoke(o, new Object[0]).toString();
		} catch (Exception e) {
			throw new RuntimeException((new StringBuilder("The object ")).append(o)
													.append(" has not the call property ").append(name)
													.toString(), e);
		}
	}
	
	public static UserDTO getLoggedUserDTO() {
		if (JSFUtil.getSessionParameter("loggedUserDTO") == null) {
			throw new UserNotLoggedException("User is not logged.");
		}
		return (UserDTO) JSFUtil.getSessionParameter("loggedUserDTO");
	}
	
	public static boolean isLoggedUser() {
		return JSFUtil.getSessionParameter("loggedUserDTO") != null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String managedBeanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    if (context == null) {
	   	 logger.warn("context is null");
	   	 return null;
	    }
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + managedBeanName + "}", Object.class);
	}
	
	public static String getCurrentIP() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
													.getCurrentInstance().getExternalContext().getRequest();
		return request.getRemoteAddr();
	}
	
}
