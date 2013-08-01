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
public class ArtifactDependencyTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "artifactdependencytype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="artifactdependencytype_generator")
	private Integer artifactdependencytypePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getArtifactdependencytypePk() {
		return artifactdependencytypePk;
	}

	public void setArtifactdependencytypePk(Integer artifactdependencytypePk) {
		this.artifactdependencytypePk = artifactdependencytypePk;
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
		result = prime
				* result
				+ ((artifactdependencytypePk == null) ? 0
						: artifactdependencytypePk.hashCode());
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
		ArtifactDependencyTypeDTO other = (ArtifactDependencyTypeDTO) obj;
		if (artifactdependencytypePk == null) {
			if (other.artifactdependencytypePk != null)
				return false;
		} else if (!artifactdependencytypePk
				.equals(other.artifactdependencytypePk))
			return false;
		return true;
	}
}