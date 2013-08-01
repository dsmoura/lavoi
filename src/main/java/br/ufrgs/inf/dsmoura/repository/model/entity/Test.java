package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Test implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "test_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="test_generator")
	private Integer testPk;
	
	@ManyToMany
	@JoinColumn(name="testpk")
	private List<TestTypeDTO> testTypeDTOs = new ArrayList<TestTypeDTO>();
	
	@ManyToMany
	@JoinColumn(name="testpk")
	private List<TestMethodTypeDTO> testMethodTypeDTOs = new ArrayList<TestMethodTypeDTO>();
	
	@ManyToMany
	@JoinColumn(name="testpk")
	private List<DesignPatternDTO> designPatternDTOs = new ArrayList<DesignPatternDTO>();
	
	@ManyToMany
	@JoinColumn(name="testpk")
	private List<ProgrammingLanguageDTO> programmingLanguageDTOs = new ArrayList<ProgrammingLanguageDTO>();
	
	@Column
	private String otherProgrammingLanguage;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="artifact", referencedColumnName="testpk")
	private List<ArtifactDTO> artifactDTOs = new ArrayList<ArtifactDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="sourcecode", referencedColumnName="testpk")
	private List<SourceCodeDTO> sourceCodeDTOs = new ArrayList<SourceCodeDTO>();

	public Integer getTestPk() {
		return testPk;
	}

	public void setTestPk(Integer testPk) {
		this.testPk = testPk;
	}
	
	public List<TestTypeDTO> getTestTypeDTOs() {
		return testTypeDTOs;
	}

	public void setTestTypeDTOs(List<TestTypeDTO> testTypeDTOs) {
		this.testTypeDTOs = testTypeDTOs;
	}

	public List<TestMethodTypeDTO> getTestMethodTypeDTOs() {
		return testMethodTypeDTOs;
	}

	public void setTestMethodTypeDTOs(List<TestMethodTypeDTO> testMethodTypeDTOs) {
		this.testMethodTypeDTOs = testMethodTypeDTOs;
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

	public void setProgrammingLanguageDTOs(
			List<ProgrammingLanguageDTO> programmingLanguageDTOs) {
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

	public List<SourceCodeDTO> getSourceCodeDTOs() {
		return sourceCodeDTOs;
	}

	public void setSourceCodeDTOs(List<SourceCodeDTO> sourceCodeDTOs) {
		this.sourceCodeDTOs = sourceCodeDTOs;
	}
}
