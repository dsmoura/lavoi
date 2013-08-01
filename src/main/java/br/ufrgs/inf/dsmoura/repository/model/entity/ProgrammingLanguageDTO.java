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
public class ProgrammingLanguageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "programminglanguage_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="programminglanguage_generator")
	private Integer programminglanguagePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getProgramminglanguagePk() {
		return programminglanguagePk;
	}

	public void setProgramminglanguagePk(Integer programminglanguagePk) {
		this.programminglanguagePk = programminglanguagePk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((programminglanguagePk == null) ? 0 : programminglanguagePk
						.hashCode());
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
		ProgrammingLanguageDTO other = (ProgrammingLanguageDTO) obj;
		if (programminglanguagePk == null) {
			if (other.programminglanguagePk != null)
				return false;
		} else if (!programminglanguagePk.equals(other.programminglanguagePk))
			return false;
		return true;
	}
}