package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class OrganizationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "organization_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="organization_generator")
	
	private Integer organizationPk;
	
	@OneToMany
	@JoinColumn(name="organizationpk")
	private List<ProjectDTO> projectDTOs = new ArrayList<ProjectDTO>();
	
	@Column(nullable=false)
	private String name;

	
	public Integer getOrganizationPk() {
		return organizationPk;
	}

	public void setOrganizationPk(Integer organizationPk) {
		this.organizationPk = organizationPk;
	}
	
	public List<ProjectDTO> getProjectDTOs() {
		return projectDTOs;
	}

	public void setProjectDTOs(List<ProjectDTO> projectDTOs) {
		this.projectDTOs = projectDTOs;
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
				+ ((organizationPk == null) ? 0 : organizationPk.hashCode());
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
		OrganizationDTO other = (OrganizationDTO) obj;
		if (organizationPk == null) {
			if (other.organizationPk != null)
				return false;
		} else if (!organizationPk.equals(other.organizationPk))
			return false;
		return true;
	}
	
}