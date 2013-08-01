package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity

public class ApplicationSubdomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "applicationsubdomain_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="applicationsubdomain_generator")
	private Integer applicationsubdomainPk;
	
	@ManyToOne
	@JoinColumn(name="applicationdomainpk")
	private ApplicationDomain applicationDomain = new ApplicationDomain();

	@Column(nullable=false)
	private String name;
	
	public Integer getApplicationsubdomainPk() {
		return applicationsubdomainPk;
	}

	public void setApplicationsubdomainPk(Integer applicationsubdomainPk) {
		this.applicationsubdomainPk = applicationsubdomainPk;
	}

	public ApplicationDomain getApplicationDomain() {
		return applicationDomain;
	}

	public void setApplicationDomain(ApplicationDomain applicationDomain) {
		this.applicationDomain = applicationDomain;
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
				+ ((applicationsubdomainPk == null) ? 0 : applicationsubdomainPk
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
		ApplicationSubdomain other = (ApplicationSubdomain) obj;
		if (applicationsubdomainPk == null) {
			if (other.applicationsubdomainPk != null)
				return false;
		} else if (!applicationsubdomainPk.equals(other.applicationsubdomainPk))
			return false;
		return true;
	}
	
}