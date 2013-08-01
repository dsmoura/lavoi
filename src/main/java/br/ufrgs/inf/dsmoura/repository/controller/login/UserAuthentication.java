package br.ufrgs.inf.dsmoura.repository.controller.login;

import br.ufrgs.inf.dsmoura.repository.controller.exception.UserNotFoundException;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;

public interface UserAuthentication {
	public String DATABASE_MODE = "DATABASE";
	public String LDAP_MODE = "LDAP";

	public boolean authenticateUser(String username, String password) throws UserNotFoundException;
}