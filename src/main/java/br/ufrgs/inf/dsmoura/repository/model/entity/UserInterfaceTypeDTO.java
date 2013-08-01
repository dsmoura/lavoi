package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class UserInterfaceTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "userinterfacetype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="userinterfacetype_generator")
	private Integer userinterfacetypePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getUserinterfacetypePk() {
		return userinterfacetypePk;
	}
	public void setUserinterfacetypePk(Integer userinterfacetypePk) {
		this.userinterfacetypePk = userinterfacetypePk;
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
				+ ((userinterfacetypePk == null) ? 0 : userinterfacetypePk
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
		UserInterfaceTypeDTO other = (UserInterfaceTypeDTO) obj;
		if (userinterfacetypePk == null) {
			if (other.userinterfacetypePk != null)
				return false;
		} else if (!userinterfacetypePk.equals(other.userinterfacetypePk))
			return false;
		return true;
	}
}
