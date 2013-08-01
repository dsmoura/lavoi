package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Design implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "design_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="design_generator")
	private Integer designPk;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="designpk")
	private List<InterfaceSpecDTO> interfaceSpecDTOs = new ArrayList<InterfaceSpecDTO>();
	
	@ManyToMany
	@JoinColumn(name="designpk")
	private List<DesignPatternDTO> designPatternDTOs = new ArrayList<DesignPatternDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="userinterface", referencedColumnName="designpk")
	private List<UserInterfaceDTO> userInterfaceDTOs = new ArrayList<UserInterfaceDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="artifact", referencedColumnName="designpk")
	private List<ArtifactDTO> artifactDTOs = new ArrayList<ArtifactDTO>();
	
	public Integer getDesignPk() {
		return designPk;
	}

	public void setDesignPk(Integer designPk) {
		this.designPk = designPk;
	}

	public List<InterfaceSpecDTO> getInterfaceSpecDTOs() {
		return interfaceSpecDTOs;
	}

	public void setInterfaceSpecDTOs(List<InterfaceSpecDTO> interfaceSpecDTOs) {
		this.interfaceSpecDTOs = interfaceSpecDTOs;
	}

	public List<DesignPatternDTO> getDesignPatternDTOs() {
		return designPatternDTOs;
	}

	public void setDesignPatternDTOs(List<DesignPatternDTO> designPatternDTOs) {
		this.designPatternDTOs = designPatternDTOs;
	}

	public List<UserInterfaceDTO> getUserInterfaceDTOs() {
		return userInterfaceDTOs;
	}

	public void setUserInterfaceDTOs(List<UserInterfaceDTO> userInterfaceDTOs) {
		this.userInterfaceDTOs = userInterfaceDTOs;
	}

	public List<ArtifactDTO> getArtifactDTOs() {
		return artifactDTOs;
	}

	public void setArtifactDTOs(List<ArtifactDTO> artifactDTOs) {
		this.artifactDTOs = artifactDTOs;
	}
	
}
