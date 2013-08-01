package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Analysis implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "analysis_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="analysis_generator")
	private Integer analysisPk;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="analysispk")
	private List<InterfaceSpecDTO> interfaceSpecDTOs = new ArrayList<InterfaceSpecDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="usecase", referencedColumnName="usecasepk")
	private List<UseCaseDTO> useCaseDTOs = new ArrayList<UseCaseDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="userinterface", referencedColumnName="analysispk")
	private List<UserInterfaceDTO> userInterfaceDTOs = new ArrayList<UserInterfaceDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="artifact", referencedColumnName="analysispk")
	private List<ArtifactDTO> artifactDTOs = new ArrayList<ArtifactDTO>();

	public Integer getAnalysisPk() {
		return analysisPk;
	}

	public void setAnalysisPk(Integer analysisPk) {
		this.analysisPk = analysisPk;
	}

	public List<InterfaceSpecDTO> getInterfaceSpecDTOs() {
		return interfaceSpecDTOs;
	}

	public void setInterfaceSpecDTOs(List<InterfaceSpecDTO> interfaceSpecDTOs) {
		this.interfaceSpecDTOs = interfaceSpecDTOs;
	}
	
	public List<UseCaseDTO> getUseCaseDTOs() {
		return useCaseDTOs;
	}

	public void setUseCaseDTOs(List<UseCaseDTO> useCaseDTOs) {
		this.useCaseDTOs = useCaseDTOs;
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
