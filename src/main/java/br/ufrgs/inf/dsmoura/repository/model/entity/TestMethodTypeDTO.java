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
public class TestMethodTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "testmethodtype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="testmethodtype_generator")
	private Integer testmethodtypePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getTestmethodtypePk() {
		return testmethodtypePk;
	}
	public void setTestmethodtypePk(Integer testmethodtypePk) {
		this.testmethodtypePk = testmethodtypePk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((testmethodtypePk == null) ? 0 : testmethodtypePk.hashCode());
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
		TestMethodTypeDTO other = (TestMethodTypeDTO) obj;
		if (testmethodtypePk == null) {
			if (other.testmethodtypePk != null)
				return false;
		} else if (!testmethodtypePk.equals(other.testmethodtypePk))
			return false;
		return true;
	}
}