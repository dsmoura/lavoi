package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class SourceCodeTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "sourcecodetype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="sourcecodetype_generator")
	private Integer sourcecodetypePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getSourcecodetypePk() {
		return sourcecodetypePk;
	}
	public void setSourcecodetypePk(Integer sourcecodetypePk) {
		this.sourcecodetypePk = sourcecodetypePk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sourcecodetypePk == null) ? 0 : sourcecodetypePk.hashCode());
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
		SourceCodeTypeDTO other = (SourceCodeTypeDTO) obj;
		if (sourcecodetypePk == null) {
			if (other.sourcecodetypePk != null)
				return false;
		} else if (!sourcecodetypePk.equals(other.sourcecodetypePk))
			return false;
		return true;
	}
}
