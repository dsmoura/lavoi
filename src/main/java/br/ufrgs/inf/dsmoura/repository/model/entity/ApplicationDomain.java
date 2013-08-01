package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
   @NamedQuery(name="ApplicationDomain.getByName",
       query="SELECT a" +
       			" FROM ApplicationDomain a" +
       			"  WHERE a.name = :name")
})
public class ApplicationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "applicationdomain_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="applicationdomain_generator")
	private Integer applicationdomainPk;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	@OneToMany
	@JoinColumn(name="applicationdomainpk")
	private List<ApplicationSubdomain> applicationSubdomains = new ArrayList<ApplicationSubdomain>();

	
	public Integer getApplicationdomainPk() {
		return applicationdomainPk;
	}

	public void setApplicationdomainPk(Integer applicationdomainPk) {
		this.applicationdomainPk = applicationdomainPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ApplicationSubdomain> getApplicationSubdomains() {
		return applicationSubdomains;
	}

	public void setApplicationSubdomains(List<ApplicationSubdomain> applicationSubdomains) {
		this.applicationSubdomains = applicationSubdomains;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((applicationdomainPk == null) ? 0 : applicationdomainPk
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
		ApplicationDomain other = (ApplicationDomain) obj;
		if (applicationdomainPk == null) {
			if (other.applicationdomainPk != null)
				return false;
		} else if (!applicationdomainPk.equals(other.applicationdomainPk))
			return false;
		return true;
	}
	
}