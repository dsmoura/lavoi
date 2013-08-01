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
public class FunctionalRequirementTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "functionalrequirementtype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="functionalrequirementtype_generator")
	private Integer functionalrequirementtypePk;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	public Integer getFunctionalrequirementtypePk() {
		return functionalrequirementtypePk;
	}
	public void setFunctionalrequirementtypePk(Integer functionalrequirementtypePk) {
		this.functionalrequirementtypePk = functionalrequirementtypePk;
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
				+ ((functionalrequirementtypePk == null) ? 0
						: functionalrequirementtypePk.hashCode());
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
		FunctionalRequirementTypeDTO other = (FunctionalRequirementTypeDTO) obj;
		if (functionalrequirementtypePk == null) {
			if (other.functionalrequirementtypePk != null)
				return false;
		} else if (!functionalrequirementtypePk
				.equals(other.functionalrequirementtypePk))
			return false;
		return true;
	}
}