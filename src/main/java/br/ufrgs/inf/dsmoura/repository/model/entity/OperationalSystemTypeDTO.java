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
public class OperationalSystemTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "operationalsystemtype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="operationalsystemtype_generator")
	private Integer operationalsystemtypePk;
	
	public Integer getOperationalsystemtypePk() {
		return operationalsystemtypePk;
	}
	public void setOperationalsystemtypePk(Integer operationalsystemtypePk) {
		this.operationalsystemtypePk = operationalsystemtypePk;
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
				+ ((operationalsystemtypePk == null) ? 0 : operationalsystemtypePk
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
		OperationalSystemTypeDTO other = (OperationalSystemTypeDTO) obj;
		if (operationalsystemtypePk == null) {
			if (other.operationalsystemtypePk != null)
				return false;
		} else if (!operationalsystemtypePk.equals(other.operationalsystemtypePk))
			return false;
		return true;
	}
}