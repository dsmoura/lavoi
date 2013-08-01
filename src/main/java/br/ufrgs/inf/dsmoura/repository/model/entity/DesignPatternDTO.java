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
public class DesignPatternDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "designpattern_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="designpattern_generator")
	private Integer designpatternPk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getDesignpatternPk() {
		return designpatternPk;
	}

	public void setDesignpatternPk(Integer designpatternPk) {
		this.designpatternPk = designpatternPk;
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
		result = prime * result
				+ ((designpatternPk == null) ? 0 : designpatternPk.hashCode());
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
		DesignPatternDTO other = (DesignPatternDTO) obj;
		if (designpatternPk == null) {
			if (other.designpatternPk != null)
				return false;
		} else if (!designpatternPk.equals(other.designpatternPk))
			return false;
		return true;
	}
}