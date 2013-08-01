package br.ufrgs.inf.dsmoura.repository.controller.login;

import br.ufrgs.inf.dsmoura.repository.controller.exception.UserNotFoundException;
import br.ufrgs.inf.dsmoura.repository.controller.util.SecurityUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.UserDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;

public class DatabaseUserAuthentication implements UserAuthentication {
	
	@Override
	public boolean authenticateUser(String username, String password) throws UserNotFoundException {
		UserDTO userWithUsernameDTO = UserDAO.getInstance().findByUsername(username);
		if (userWithUsernameDTO == null) {
			throw new UserNotFoundException();
		}
		String hashedPassword = SecurityUtil.generateHash(password);
		return hashedPassword.equals(userWithUsernameDTO.getPassword());
	}
	
}
