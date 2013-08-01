package br.ufrgs.inf.dsmoura.repository.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

public class GenericDAO implements DAO {

	private static GenericDAO instance;
	
	public synchronized static GenericDAO getInstance() {
		if (instance == null) {
			instance = new GenericDAO();
		}
		return instance;
	}
	
	protected EntityManager createEntityManager() {
		return Persistence.createEntityManagerFactory("lavoi").createEntityManager();
	}
	
	public Serializable insert(Serializable entity) {
		EntityManager entityManager = createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		if ( ! transaction.isActive() ) {
			transaction.begin();
		}
		try {
			entityManager.persist(entity);
			transaction.commit();
			return entity;
		} catch (RollbackException rE) {
			throw rE;
		} catch (Exception e) {
			transaction.rollback();
			throw new RuntimeException(e);
		}
	}
	
	public Serializable update(Serializable entity) {
		EntityManager entityManager = createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();  
      transaction.begin();  
      try{  
      	entityManager.merge(entity);
          transaction.commit();  
          return entity;    
      } catch (RollbackException rE) {
			throw rE;
		} catch(Exception e){  
          transaction.rollback();  
          throw new RuntimeException(e);  
      }  
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Serializable findUnique(Class entityClass, Integer entityPk) {
		EntityManager entityManager = createEntityManager();
		return entityManager.find(entityClass, entityPk);
	}

	public List<Serializable> listAll(Serializable entity) {
		throw new RuntimeException("this method is not implemented.");
	}

	public Serializable delete(Serializable entity) {
		EntityManager entityManager = createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		try {
			entityManager.remove(entity);
			transaction.commit();
			return entity;
		} catch (RollbackException rE) {
			throw rE;
		} catch (Exception e) {
			transaction.rollback();
			throw new RuntimeException(e);
		}
	}

}