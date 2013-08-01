package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@NamedQueries({
   @NamedQuery(name="SoftwareLicenseDTO.findByName",
       query="SELECT sl" +
       			" FROM SoftwareLicenseDTO sl" +
       			" WHERE sl.name = :name")
})
@Entity
public class SoftwareLicenseDTO implements Serializable, Comparable<SoftwareLicenseDTO> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "softwarelicense_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="softwarelicense_generator")
	private Integer softwarelicensePk;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	@Column(nullable=false)
	private Boolean isGPLCompatible;
	
	public Integer getSoftwarelicensePk() {
		return softwarelicensePk;
	}
	public void setSoftwarelicensePk(Integer softwarelicensePk) {
		this.softwarelicensePk = softwarelicensePk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsGPLCompatible() {
		return isGPLCompatible;
	}
	public void setIsGPLCompatible(Boolean isGPLCompatible) {
		this.isGPLCompatible = isGPLCompatible;
	}
	public String toString() {
		return this.getName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((softwarelicensePk == null) ? 0 : softwarelicensePk.hashCode());
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
		SoftwareLicenseDTO other = (SoftwareLicenseDTO) obj;
		if (softwarelicensePk == null) {
			if (other.softwarelicensePk != null)
				return false;
		} else if (!softwarelicensePk.equals(other.softwarelicensePk))
			return false;
		return true;
	}
	@Override
	public int compareTo(SoftwareLicenseDTO o) {
		return this.getName().compareTo( o.getName() );
	}
}