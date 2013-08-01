package br.ufrgs.inf.dsmoura.repository.controller;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import org.ajax4jsf.model.KeepAlive;

import br.ufrgs.inf.dsmoura.repository.controller.asset.NavigationMB;
import br.ufrgs.inf.dsmoura.repository.controller.exception.UserNotFoundException;
import br.ufrgs.inf.dsmoura.repository.controller.exception.UserNotLoggedException;
import br.ufrgs.inf.dsmoura.repository.controller.login.LDAPUserAuthentication;
import br.ufrgs.inf.dsmoura.repository.controller.login.UserAuthentication;
import br.ufrgs.inf.dsmoura.repository.controller.util.EmailUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.SecurityUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.GenericDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.UserDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.LogUserLoginDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;
import br.ufrgs.inf.dsmoura.repository.model.loadData.LoadLists;


@KeepAlive
public class LoginMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UserDTO userForLoginDTO = new UserDTO();
	private UserDTO userDTOForRegistration;
	
	public Boolean getIsDatabaseUserAuthentication() {
		String userAuthenticationMode = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.USER_AUTHENTICATION_MODE);
		return userAuthenticationMode.equalsIgnoreCase(UserAuthentication.DATABASE_MODE);
	}
	
	public Boolean getIsLDAPUserAuthentication() {
		String userAuthenticationMode = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.USER_AUTHENTICATION_MODE);
		return userAuthenticationMode.equalsIgnoreCase(UserAuthentication.LDAP_MODE);
	}
	
	public String validateLoginLDAP() {
		LDAPUserAuthentication ua = new LDAPUserAuthentication();
		boolean isValidUser;
		try {
			isValidUser = ua.authenticateUser(userForLoginDTO.getUsername(), userForLoginDTO.getPassword());
		} catch (UserNotFoundException e) {
			String userDomainPrefix = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.USER_AUTHENTICATION_DOMAIN_PREFIX);
			String message;
			if (userDomainPrefix != null && !userForLoginDTO.getUsername().startsWith(userDomainPrefix)) {
					message = "Invalid login. Use the prefix for your username: " + userDomainPrefix;
			}
			else {
				message = "Invalid login.";
			}
				
			FacesContext.getCurrentInstance().addMessage("usernameID", new FacesMessage(message));
			return "";
		}
		if (isValidUser) {
			/* login is valid, verify to store the user in database or query the user */
			UserDTO userWithUsernameDTO = ua.verifyDatabaseUser(userForLoginDTO.getUsername(), userForLoginDTO.getPassword());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUserDTO", userWithUsernameDTO);
			return NavigationMB.MAIN;
		}
		else {
			FacesContext.getCurrentInstance().addMessage("usernameID", new FacesMessage("Invalid login."));
			return "";
		}
	}
	
	public String validateLoginDatabase() {
		/* Validate the email */
		UserDTO userWithEmailDTO = UserDAO.getInstance().findByEmail(userForLoginDTO.getEmail());
		if (userWithEmailDTO == null) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("E-mail not found."));
			return "";
		}
		/* Validate the password */
		String password;
		if (userWithEmailDTO.getIsChangingPassword()) {
			/* Password without hashing */
			password = userForLoginDTO.getPassword();
		} else {
			/* Normal cases, hashing passwords */
			password = SecurityUtil.generateHash(userForLoginDTO.getPassword());
		}
		if (!password.equals(userWithEmailDTO.getPassword())) {
			FacesContext.getCurrentInstance().addMessage("passwordID", new FacesMessage("Wrong password."));
			return "";
		}
		/* Verify if it is the first login */
		if (userWithEmailDTO.getIsFirstLogin()) {
			/* Validate the filling of the code */
			if (userForLoginDTO.getFirstLoginCode() == null ||
					userForLoginDTO.getFirstLoginCode().length() == 0) {
				FacesContext.getCurrentInstance().addMessage("codeID", new FacesMessage("Enter the code."));
				return "";
			}
			if ( userForLoginDTO.getFirstLoginCode().equalsIgnoreCase( userWithEmailDTO.getFirstLoginCode() ) ) {
				userWithEmailDTO.setIsFirstLogin(false);
				GenericDAO.getInstance().update(userWithEmailDTO);
			}
			else {
				FacesContext.getCurrentInstance().addMessage("codeID", new FacesMessage("Wrong code."));
				return "";
			}
		}
		/* Validate if it is changing password */
		if (userWithEmailDTO.getIsChangingPassword()) {
			/* Validates the filling the new password */
			if ( userForLoginDTO.getNewPassword() == null ||
					userForLoginDTO.getNewPassword().length() == 0) {
				FacesContext.getCurrentInstance().addMessage("newPasswordID", new FacesMessage("Enter the new password."));
				return "";
			}
			/* Validates the filling the new password */
			if ( userForLoginDTO.getConfirmPassword() == null ||
					userForLoginDTO.getConfirmPassword().length() == 0) {
				FacesContext.getCurrentInstance().addMessage("confirmNewPasswordID", new FacesMessage("Confirm the new password."));
				return "";
			}
			/* Validate confirm password */
			if ( ! userForLoginDTO.getNewPassword().equals( userForLoginDTO.getConfirmPassword()) ) {
				FacesContext.getCurrentInstance().addMessage("confirmNewPasswordID", new FacesMessage("Passwords don't match."));
				return "";
			}
			/* Renew the password */
			String hashedPassword = SecurityUtil.generateHash(userForLoginDTO.getNewPassword());
			userWithEmailDTO.setPassword( hashedPassword );
			userWithEmailDTO.setIsChangingPassword(false);
			GenericDAO.getInstance().update(userWithEmailDTO);
		}
		
		/* Register the log user login */
		LogUserLoginDTO logUserLoginDTO = new LogUserLoginDTO();
		logUserLoginDTO.setUsername(userWithEmailDTO.getUsername());
		logUserLoginDTO.setFullname(userWithEmailDTO.getName());
		logUserLoginDTO.setEmail(userWithEmailDTO.getEmail());
		logUserLoginDTO.setDate(Calendar.getInstance());
		logUserLoginDTO.setIp(JSFUtil.getCurrentIP());
		GenericDAO.getInstance().insert(logUserLoginDTO);
		
		/* Register the user in session */
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUserDTO", userWithEmailDTO);
		
		/* Enter in the repository */
		return NavigationMB.MAIN;
	}
	
	public String registerLogin() {
		/* Validate email already registered */
		UserDTO userWithEmailDTO = UserDAO.getInstance().findByEmail(userDTOForRegistration.getEmail());
		if (userWithEmailDTO != null) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("E-mail already registered."));
			return "";
		}
		
		if (this.isEmailDomainRestricted() &&
				! userDTOForRegistration.getEmail().endsWith( this.getEmailDomainRestriction() )) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("It's mandatory the email with the domain: " + this.getEmailDomainRestriction()));
			return "";
		}
		
		/* Validate confirm password */
		if ( ! userDTOForRegistration.getPassword().equals( userDTOForRegistration.getConfirmPassword()) ) {
			FacesContext.getCurrentInstance().addMessage("confirmPasswordID", new FacesMessage("Passwords don't match."));
			return "";
		}
		
		UserDTO newUserDTO = new UserDTO();
		
		newUserDTO.setUsername(userDTOForRegistration.getUsername());
		newUserDTO.setName(userDTOForRegistration.getName());
		newUserDTO.setEmail(userDTOForRegistration.getEmail());
		
		String hashedPassword = SecurityUtil.generateHash(userDTOForRegistration.getPassword());
		newUserDTO.setPassword(hashedPassword);
		
		newUserDTO.setIsCertifier(false);
		newUserDTO.setIsManager(false);
		
		newUserDTO.setFirstLoginCode( FieldsUtil.getRandomCode() );
		newUserDTO.setIsFirstLogin(true);
		
		newUserDTO.setIsChangingPassword(false);
		
		newUserDTO.setIp(JSFUtil.getCurrentIP());
		
		GenericDAO.getInstance().insert(newUserDTO);
		
		EmailUtil.sendMail(newUserDTO.getEmail(),
      							"Registration on Lavoi",
      							"Welcome " + newUserDTO.getName() + ",\n\n" +
      							"You have registered on Lavoi - Software Reuse Repository.\n\n" + 
      							"You code for e-mail confirmation is: " + newUserDTO.getFirstLoginCode() + " \n\n" +
      							"Have a good experience! Thanks!");
		this.userForLoginDTO = new UserDTO();
		return NavigationMB.REGISTERED_LOGIN;
	}
	
	public String changePassword() {
		/* Validate the email filling */
		if (userForLoginDTO.getEmail() == null ||
				userForLoginDTO.getEmail().length() == 0) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("Enter the E-mail."));
			return "";
		}
		/* Validate email already registered */
		UserDTO userForPasswordChangingDTO = UserDAO.getInstance().findByEmail(userForLoginDTO.getEmail());
		if (userForPasswordChangingDTO == null) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("E-mail not found."));
			return "";
		}
		/* Renew the password */
		userForPasswordChangingDTO.setPassword( FieldsUtil.getRandomCode() );
		userForPasswordChangingDTO.setIsChangingPassword(true);
		GenericDAO.getInstance().update(userForPasswordChangingDTO);
		/* Send email */
		EmailUtil.sendMail( userForPasswordChangingDTO.getEmail(),
      							"Password Resetting - Lavoi",
      							userForPasswordChangingDTO.getName() + ",\n\n" +
      							"Your password was reseted.\n\n" + 
      							"You temporary password is: " + userForPasswordChangingDTO.getPassword() + " \n\n" +
      							"Thanks");
		return NavigationMB.CHANGED_PASSWORD_LOGIN;
	}
	
	public String resendRegistrationCode() {
		/* Validate the email filling */
		if (userForLoginDTO.getEmail() == null ||
				userForLoginDTO.getEmail().length() == 0) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("Enter the E-mail."));
			return "";
		}
		/* Validate email already registered */
		UserDTO userDTOForResendCode = UserDAO.getInstance().findByEmail(userForLoginDTO.getEmail());
		if (userDTOForResendCode == null) {
			FacesContext.getCurrentInstance().addMessage("emailID", new FacesMessage("E-mail not found."));
			return "";
		}
		EmailUtil.sendMail(userDTOForResendCode.getEmail(),
				"Registration Code for Lavoi",
				userDTOForResendCode.getName() + ",\n\n" +
				"You code for e-mail confirmation is: " + userDTOForResendCode.getFirstLoginCode() + " \n\n" +
				"Have a good experience! Thanks!");
		return NavigationMB.RESEND_CODE_LOGIN;
	}
	
	public String openRegisterLogin() {
		this.userDTOForRegistration = new UserDTO();
		this.userForLoginDTO = new UserDTO();
		return NavigationMB.REGISTER_LOGIN;
	}
	
	public void searchEmail() {
		UserDTO userWithEmailDTO = UserDAO.getInstance().findByEmail(userForLoginDTO.getEmail());
		if (userWithEmailDTO != null) {
			userForLoginDTO.setIsFirstLogin( userWithEmailDTO.getIsFirstLogin() );
			userForLoginDTO.setIsChangingPassword( userWithEmailDTO.getIsChangingPassword() );
		} else {
			userForLoginDTO.setIsFirstLogin(false);
			userForLoginDTO.setIsChangingPassword(false);
		}
	}
	
	public String logoutUser() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loggedUserDTO");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		this.userForLoginDTO = new UserDTO();
		return NavigationMB.LOGIN;
	}
	
	public Boolean getIsUserLogged() {
		return JSFUtil.isLoggedUser();
	}
	
	public UserDTO getLoggedUserDTO() {
		try {
			return JSFUtil.getLoggedUserDTO();
		}
		catch (UserNotLoggedException e) {
			return null;
		}
	}
	
	public void loadUsername() {
		if (this.isEmailDomainRestricted()) {
			if (userDTOForRegistration.getEmail() != null) {
				String username = userDTOForRegistration.getEmail().substring(0, userDTOForRegistration.getEmail().indexOf("@"));
				userDTOForRegistration.setUsername(username);
			}
		}
	}
	
	public String getEmailDomainRestriction() {
		return TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.EMAIL_DOMAIN_RESTRICTION);
		
	}
	
	public boolean isEmailDomainRestricted() {
		String domain = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.EMAIL_DOMAIN_RESTRICTION);
		return domain != null &&
				domain.length() > 0;
	}
	
	public Boolean getAreEmptyLists() {
		return TypesDAO.getInstance().getAssetTypeList().size() == 0 &&
				TypesDAO.getInstance().getAssetStateTypeList().size() == 0 &&
				TypesDAO.getInstance().getSoftwareLicenseDTOList().size() == 0 &&
				TypesDAO.getInstance().getDesignPatternDTOList().size() == 0;
	}
	
	public String loadAllLists() {
		if (TypesDAO.getInstance().getAssetTypeList().size() == 0 &&
				TypesDAO.getInstance().getAssetStateTypeList().size() == 0 &&
				TypesDAO.getInstance().getSoftwareLicenseDTOList().size() == 0 &&
				TypesDAO.getInstance().getDesignPatternDTOList().size() == 0) {
			LoadLists.loadAllLists();
			return "resetApplicationServer";
		}
		else {
			throw new RuntimeException("Lists cannot be loaded.");
		}
	}
	
	public String getUserIP() {
		return JSFUtil.getCurrentIP();
	}

	public UserDTO getUserForLoginDTO() {
		return userForLoginDTO;
	}

	public void setUserForLoginDTO(UserDTO userForLoginDTO) {
		this.userForLoginDTO = userForLoginDTO;
	}

	public UserDTO getUserDTOForRegistration() {
		return userDTOForRegistration;
	}

	public void setUserDTOForRegistration(UserDTO userDTOForRegistration) {
		this.userDTOForRegistration = userDTOForRegistration;
	}
	
}
