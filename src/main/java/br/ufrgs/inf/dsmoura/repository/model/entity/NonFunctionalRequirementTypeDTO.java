package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class NonFunctionalRequirementTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "nonfunctionalrequirementtype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="nonfunctionalrequirementtype_generator")
	private Integer nonfunctionalrequirementtypePk;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	public Integer getNonfunctionalrequirementtypePk() {
		return nonfunctionalrequirementtypePk;
	}
	public void setNonfunctionalrequirementtypePk(Integer nonfunctionalrequirementtypePk) {
		this.nonfunctionalrequirementtypePk = nonfunctionalrequirementtypePk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return this.getName();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((nonfunctionalrequirementtypePk == null) ? 0
						: nonfunctionalrequirementtypePk.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonFunctionalRequirementTypeDTO other = (NonFunctionalRequirementTypeDTO) obj;
		if (nonfunctionalrequirementtypePk == null) {
			if (other.nonfunctionalrequirementtypePk != null)
				return false;
		} else if (!nonfunctionalrequirementtypePk
				.equals(other.nonfunctionalrequirementtypePk))
			return false;
		return true;
	}
}