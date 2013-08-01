package br.ufrgs.inf.dsmoura.repository.controller.login;

import java.util.ArrayList;
import java.util.Collection;

import br.ufrgs.inf.dsmoura.repository.controller.exception.UserNotFoundException;
import br.ufrgs.inf.dsmoura.repository.model.dao.UserDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;

public class LDAPUserAuthentication implements UserAuthentication {
	
	@Override
	public boolean authenticateUser(String username, String password) throws UserNotFoundException {
		//ldap code
		throw new UserNotFoundException("not implemented yet");
	}
	
	public UserDTO verifyDatabaseUser(String username, String password) {
		UserDTO userWithUsernameDTO = UserDAO.getInstance().findByUsername(username);
		if (userWithUsernameDTO != null) {
			return userWithUsernameDTO;
		}
		else {
			//ldap code
			//save the user in database
			return null;
		}
	}
	
	public Collection<String> getEmailsFromLDAP(String username) {
		Collection<String> emails = new ArrayList<String>();
		//ldap code
		return emails;
	}
	
}
