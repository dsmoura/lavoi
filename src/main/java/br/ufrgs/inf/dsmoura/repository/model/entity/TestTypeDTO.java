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
public class TestTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "testtype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="testtype_generator")
	private Integer testtypePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getTesttypePk() {
		return testtypePk;
	}
	public void setTesttypePk(Integer testtypePk) {
		this.testtypePk = testtypePk;
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
				+ ((testtypePk == null) ? 0 : testtypePk.hashCode());
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
		TestTypeDTO other = (TestTypeDTO) obj;
		if (testtypePk == null) {
			if (other.testtypePk != null)
				return false;
		} else if (!testtypePk.equals(other.testtypePk))
			return false;
		return true;
	}
}