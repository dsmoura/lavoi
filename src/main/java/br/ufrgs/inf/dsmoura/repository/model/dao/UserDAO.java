package br.ufrgs.inf.dsmoura.repository.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;

public class UserDAO extends GenericDAO {
	
	private static UserDAO instance;
	
	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	private UserDAO() {
	}
	
	public UserDTO findByEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new RuntimeException("Email is null or empty = " + email);
		}
		EntityManager entityManager = createEntityManager();
		Query query = entityManager.createNamedQuery("UserDTO.findByEmail");
		query.setParameter("email", email);

		try {
			return (UserDTO) query.getSingleResult();
		}
		catch(NoResultException nRE) {
			return null;
		}
	}
	
	public UserDTO findByUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new RuntimeException("Username is null or empty = " + username);
		}
		EntityManager entityManager = createEntityManager();
		Query query = entityManager.createNamedQuery("UserDTO.findByUsername");
		query.setParameter("username", username);

		try {
			return (UserDTO) query.getSingleResult();
		}
		catch(NoResultException nRE) {
			return null;
		}
	}
	
}
