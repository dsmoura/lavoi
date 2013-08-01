package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Implementation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "implementation_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="implementation_generator")
	private Integer implementationPk;
	
	@ManyToMany
	@JoinColumn(name="implementationpk")
	private List<DesignPatternDTO> designPatternDTOs = new ArrayList<DesignPatternDTO>();
	
	@ManyToMany
	@JoinColumn(name="implementationpk")
	private List<ProgrammingLanguageDTO> programmingLanguageDTOs = new ArrayList<ProgrammingLanguageDTO>();
	
	@Column
	private String otherProgrammingLanguage;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="artifact", referencedColumnName="implementationpk")
	private List<ArtifactDTO> artifactDTOs = new ArrayList<ArtifactDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="wsdl", referencedColumnName="implementationpk")
	private List<WsdlDTO> wsdlDTOs = new ArrayList<WsdlDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="sourcecode", referencedColumnName="implementationpk")
	private List<SourceCodeDTO> sourceCodeDTOs = new ArrayList<SourceCodeDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="userinterface", referencedColumnName="implementationpk")
	private List<UserInterfaceDTO> userInterfaceDTOs = new ArrayList<UserInterfaceDTO>();
	
	public Integer getImplementationPk() {
		return implementationPk;
	}
	
	public void setImplementationPk(Integer implementationPk) {
		this.implementationPk = implementationPk;
	}
	
	public List<DesignPatternDTO> getDesignPatternDTOs() {
		return designPatternDTOs;
	}
	
	public void setDesignPatternDTOs(List<DesignPatternDTO> designPatternDTOs) {
		this.designPatternDTOs = designPatternDTOs;
	}

	public List<ProgrammingLanguageDTO> getProgrammingLanguageDTOs() {
		return programmingLanguageDTOs;
	}

	public void setProgrammingLanguageDTOs(List<ProgrammingLanguageDTO> programmingLanguageDTOs) {
		this.programmingLanguageDTOs = programmingLanguageDTOs;
	}
	
	public String getOtherProgrammingLanguage() {
		return otherProgrammingLanguage;
	}

	public void setOtherProgrammingLanguage(String otherProgrammingLanguage) {
		this.otherProgrammingLanguage = otherProgrammingLanguage;
	}

	public List<ArtifactDTO> getArtifactDTOs() {
		return artifactDTOs;
	}

	public void setArtifactDTOs(List<ArtifactDTO> artifactDTOs) {
		this.artifactDTOs = artifactDTOs;
	}
	
	public List<WsdlDTO> getWsdlDTOs() {
		return wsdlDTOs;
	}

	public void setWsdlDTOs(List<WsdlDTO> wsdlDTOs) {
		this.wsdlDTOs = wsdlDTOs;
	}

	public List<SourceCodeDTO> getSourceCodeDTOs() {
		return sourceCodeDTOs;
	}

	public void setSourceCodeDTOs(List<SourceCodeDTO> sourceCodeDTOs) {
		this.sourceCodeDTOs = sourceCodeDTOs;
	}

	public List<UserInterfaceDTO> getUserInterfaceDTOs() {
		return userInterfaceDTOs;
	}

	public void setUserInterfaceDTOs(List<UserInterfaceDTO> userInterfaceDTOs) {
		this.userInterfaceDTOs = userInterfaceDTOs;
	}
}
