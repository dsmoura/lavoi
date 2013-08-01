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
public class InternationalizationTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "internationalizationtype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="internationalizationtype_generator")
	private Integer internationalizationtypePk;
	
	public Integer getInternationalizationtypePk() {
		return internationalizationtypePk;
	}
	public void setInternationalizationtypePk(Integer internationalizationtypePk) {
		this.internationalizationtypePk = internationalizationtypePk;
	}
	@Column(unique=true, nullable=false)
	private String name;
	
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
				+ ((internationalizationtypePk == null) ? 0
						: internationalizationtypePk.hashCode());
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
		InternationalizationTypeDTO other = (InternationalizationTypeDTO) obj;
		if (internationalizationtypePk == null) {
			if (other.internationalizationtypePk != null)
				return false;
		} else if (!internationalizationtypePk
				.equals(other.internationalizationtypePk))
			return false;
		return true;
	}
}