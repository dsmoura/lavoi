package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class ArtifactDependencyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "artifactdependency_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="artifactdependency_generator")
	private Integer artifactdependencyPk;
	
	@Column(nullable=false)
	private String artifactID;
	
	@ManyToOne
	@JoinColumn(name="artifactdependencytypepk", nullable=false)
	private ArtifactDependencyTypeDTO artifactDependencyTypeDTO;
	
	@Transient
	private Long randomID = new Random().nextLong();
	
	public Integer getArtifactdependencyPk() {
		return artifactdependencyPk;
	}

	public void setArtifactdependencyPk(Integer artifactdependencyPk) {
		this.artifactdependencyPk = artifactdependencyPk;
	}
	
	public String getArtifactID() {
		return artifactID;
	}

	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}

	public ArtifactDependencyTypeDTO getArtifactDependencyTypeDTO() {
		return artifactDependencyTypeDTO;
	}

	public void setArtifactDependencyTypeDTO(
			ArtifactDependencyTypeDTO artifactDependencyTypeDTO) {
		this.artifactDependencyTypeDTO = artifactDependencyTypeDTO;
	}

	public Long getRandomID() {
		return randomID;
	}

	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}

}