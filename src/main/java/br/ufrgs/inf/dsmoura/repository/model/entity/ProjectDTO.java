package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;

@Entity
public class ProjectDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "project_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="project_generator")
	private Integer projectPk;
	
	@ManyToOne
	@JoinColumn(name="organizationpk")
	private OrganizationDTO organizationDTO = new OrganizationDTO();

	@Column(nullable=false)
	private String name;
	
	public Integer getProjectPk() {
		return projectPk;
	}

	public void setProjectPk(Integer projectPk) {
		this.projectPk = projectPk;
	}

	public OrganizationDTO getOrganizationDTO() {
		return organizationDTO;
	}

	public void setOrganizationDTO(OrganizationDTO organizationDTO) {
		this.organizationDTO = organizationDTO;
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
				+ ((projectPk == null) ? 0 : projectPk.hashCode());
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
		ProjectDTO other = (ProjectDTO) obj;
		if (projectPk == null) {
			if (other.projectPk != null)
				return false;
		} else if (!projectPk.equals(other.projectPk))
			return false;
		return true;
	}
	
}

