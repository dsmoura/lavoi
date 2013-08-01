package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class WsdlDTO extends Artifactable implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "wsdl_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="wsdl_generator")
	private Integer wsdlPk;
	
	@Column(nullable=false)
   private String id;
	
	@Column(nullable=false)
	private String name;
	
	private String type;
	
	@Column(length=16384)
	private String description;
	
	@Column(nullable=false)
	private String reference;
	
	@Lob
	private byte[] file;
	
	@ManyToOne
	@JoinColumn(name="softwarelicensePk", nullable=true)
	private SoftwareLicenseDTO softwareLicenseDTO;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="sourcecodePk")
	private List<ArtifactDependencyDTO> artifactDependencyDTOs = new ArrayList<ArtifactDependencyDTO>();
	
	@Transient
	private Long randomID = new Random().nextLong();
	
	@Transient
	private String location;
	
	public Integer getWsdlPk() {
		return wsdlPk;
	}

	public void setWsdlPk(Integer wsdlPk) {
		this.wsdlPk = wsdlPk;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getReference() {
		return reference;
	}

	@Override
	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public byte[] getFile() {
		return file;
	}

	@Override
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	@Override
	public SoftwareLicenseDTO getSoftwareLicenseDTO() {
		return softwareLicenseDTO;
	}

	@Override
	public void setSoftwareLicenseDTO(SoftwareLicenseDTO softwareLicenseDTO) {
		this.softwareLicenseDTO = softwareLicenseDTO;
	}
	
	@Override
	public List<ArtifactDependencyDTO> getArtifactDependencyDTOs() {
		return artifactDependencyDTOs;
	}

	@Override
	public void setArtifactDependencyDTOs(List<ArtifactDependencyDTO> artifactDependencyDTOs) {
		this.artifactDependencyDTOs = artifactDependencyDTOs;
	}

	@Override
	public Integer getSize() {
		if (file != null) {
			return file.length / 1024;
		}
		return null;
	}

	@Override
	public Long getRandomID() {
		return randomID;
	}

	@Override
	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	
}
