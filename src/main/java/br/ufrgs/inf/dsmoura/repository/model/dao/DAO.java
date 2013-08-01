package br.ufrgs.inf.dsmoura.repository.model.dao;

import java.io.Serializable;
import java.util.List;

interface DAO {

	public abstract Serializable insert(Serializable entity);

	public abstract Serializable update(Serializable entity);

	@SuppressWarnings("rawtypes")
	public Serializable findUnique(Class entityClass, Integer entityPk);

	public abstract List<Serializable> listAll(Serializable entity);

	public abstract Serializable delete(Serializable entity);

}