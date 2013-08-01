package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.validator.ValidationUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Test;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;


public class TestMB implements Validation {
	public static final String GENERAL_ARTIFACT_REFERENCE_PATH = "Solution\\Test\\General Artifact";
	public static final String SOURCE_CODE_REFERENCE_PATH = "Solution\\Test\\Source Code";
	
	private AssetMB assetMB;
	
	private TestTypeDTO testTypeDTOAux;
	private TestMethodTypeDTO testMethodTypeDTOAux;
	private DesignPatternDTO designPatternDTOAux;
	private ProgrammingLanguageDTO programmingLanguageDTOAux;
	
	private ArtifactMB artifactMB;
	private ArtifactMB sourceCodeMB;
	
	public TestMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		this.initMB();
	}
	
	private void initMB() {
		testTypeDTOAux = new TestTypeDTO();
		testMethodTypeDTOAux = new TestMethodTypeDTO();
		designPatternDTOAux = new DesignPatternDTO();
		programmingLanguageDTOAux = new ProgrammingLanguageDTO();
		
		artifactMB = new ArtifactMB(this.assetMB,
                        				getDTO().getArtifactDTOs(),
                        				ArtifactType.GENERAL);
      sourceCodeMB = new ArtifactMB(this.assetMB,
                        				getDTO().getSourceCodeDTOs(),
                        				ArtifactType.SOURCE_CODE);
	}
	
	public Test getDTO() {
		return this.assetMB.getAsset().getSolution().getTest();
	}
	
	@Override
	public String validate() {
		if (testTypeDTOAux != null) {
			return "Select the test type then click in Add.";
		}
		if (testMethodTypeDTOAux != null) {
			return "Select the test method type then click in Add.";
		}
		if (programmingLanguageDTOAux != null) {
			return "Select the programming language then click in Add.";
		}
		for (SourceCodeDTO sourceCodeDTO : getDTO().getSourceCodeDTOs()) {
			if (!ValidationUtil.validate(sourceCodeDTO) ||
					sourceCodeDTO.getSourceCodeTypeDTO() == null) {
				return "Enter all blank fields of source code artifacts.";
			}
		}
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			if (!ValidationUtil.validate(artifactDTO)) {
				return "Enter all blank fields of general artifacts.";
			}
		}
		return "";
	}
	
	public List<Artifactable> getArtifacts() {
		List<Artifactable> artifacts = new ArrayList<Artifactable>();
		for (Artifactable artifactDTO : getDTO().getSourceCodeDTOs()) {
			artifactDTO.setLocation(SOURCE_CODE_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			artifactDTO.setLocation(GENERAL_ARTIFACT_REFERENCE_PATH);
		}
		artifacts.addAll(getDTO().getSourceCodeDTOs());
		artifacts.addAll(getDTO().getArtifactDTOs());
		return artifacts;
	}
	
	public Boolean getHasTest() {
		return getDTO().getArtifactDTOs().size() > 0 ||
				getDTO().getDesignPatternDTOs().size() > 0 ||
				getDTO().getProgrammingLanguageDTOs().size() > 0 ||
				getDTO().getSourceCodeDTOs().size() > 0 ||
				getDTO().getTestMethodTypeDTOs().size() > 0 ||
				getDTO().getTestTypeDTOs().size() > 0;
	}
	
	/* TEST TYPE */
	public String addTestType() {
		if ((testTypeDTOAux != null) && (testTypeDTOAux.getTesttypePk() != null)) {
			if ( ! getDTO().getTestTypeDTOs().contains( testTypeDTOAux ) ) {
				getDTO().getTestTypeDTOs().add( testTypeDTOAux );
			}
			testTypeDTOAux = new TestTypeDTO();
		}
		return "";
	}
	
	public String removeTestType() {
		int testTypePkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("testTypePkToRemove") );
		for (TestTypeDTO testTypeDTO : getDTO().getTestTypeDTOs()) {
			if (testTypeDTO.getTesttypePk() == testTypePkToRemove) {
				getDTO().getTestTypeDTOs().remove( testTypeDTO );
				break;
			}
		}
		return "";
	}
	
	/* TEST METHOD TYPE */
	public String addTestMethodType() {
		if ((testMethodTypeDTOAux != null) && (testMethodTypeDTOAux.getTestmethodtypePk() != null)) {
			if ( ! getDTO().getTestMethodTypeDTOs().contains( testMethodTypeDTOAux ) ) {
				getDTO().getTestMethodTypeDTOs().add( testMethodTypeDTOAux );
			}
			testMethodTypeDTOAux = new TestMethodTypeDTO();
		}
		return "";
	}
	
	public String removeTestMethodType() {
		int testMethodTypePkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("testMethodTypePkToRemove") );
		for (TestMethodTypeDTO testMethodTypeDTO : getDTO().getTestMethodTypeDTOs()) {
			if (testMethodTypeDTO.getTestmethodtypePk() == testMethodTypePkToRemove) {
				getDTO().getTestMethodTypeDTOs().remove( testMethodTypeDTO );
				break;
			}
		}
		return "";
	}
	
	/* DESIGN PATTERNS */
	public String addDesignPattern() {
		if ((designPatternDTOAux != null) && (designPatternDTOAux.getDesignpatternPk() != null)) {
			if ( ! getDTO().getDesignPatternDTOs().contains( designPatternDTOAux ) ) {
				getDTO().getDesignPatternDTOs().add( designPatternDTOAux );
			}
			designPatternDTOAux = new DesignPatternDTO();
		}
		return "";
	}
	
	public String removeDesignPattern() {
		int designPatternPkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("designPatternPkToRemove") );
		for (DesignPatternDTO designPatternDTO : getDTO().getDesignPatternDTOs()) {
			if (designPatternDTO.getDesignpatternPk() == designPatternPkToRemove) {
				getDTO().getDesignPatternDTOs().remove( designPatternDTO );
				break;
			}
		}
		return "";
	}
	
	/* PROGRAMMING LANGUAGES */
	public String addProgrammingLanguage() {
		if ((programmingLanguageDTOAux != null) && (programmingLanguageDTOAux.getProgramminglanguagePk() != null)) {
			if ( ! getDTO().getProgrammingLanguageDTOs().contains( programmingLanguageDTOAux ) ) {
				getDTO().getProgrammingLanguageDTOs().add( programmingLanguageDTOAux );
			}
			programmingLanguageDTOAux = new ProgrammingLanguageDTO();
		}
		return "";
	}
	
	public String removeProgrammingLanguage() {
		int programmingLanguagePkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("programmingLanguagePkToRemove") );
		for (ProgrammingLanguageDTO programmingLanguageDTO : getDTO().getProgrammingLanguageDTOs()) {
			if (programmingLanguageDTO.getProgramminglanguagePk() == programmingLanguagePkToRemove) {
				getDTO().getProgrammingLanguageDTOs().remove( programmingLanguageDTO );
				break;
			}
		}
		return "";
	}
	
	public ArtifactMB getArtifactMB() {
		return artifactMB;
	}

	public void setArtifactMB(ArtifactMB artifactMB) {
		this.artifactMB = artifactMB;
	}

	public ArtifactMB getSourceCodeMB() {
		return sourceCodeMB;
	}

	public void setSourceCodeMB(ArtifactMB sourceCodeMB) {
		this.sourceCodeMB = sourceCodeMB;
	}

	public DesignPatternDTO getDesignPatternDTOAux() {
		return designPatternDTOAux;
	}

	public void setDesignPatternDTOAux(DesignPatternDTO designPatternDTOAux) {
		this.designPatternDTOAux = designPatternDTOAux;
	}

	public ProgrammingLanguageDTO getProgrammingLanguageDTOAux() {
		return programmingLanguageDTOAux;
	}

	public void setProgrammingLanguageDTOAux(
			ProgrammingLanguageDTO programmingLanguageDTOAux) {
		this.programmingLanguageDTOAux = programmingLanguageDTOAux;
	}
	
	public TestTypeDTO getTestTypeDTOAux() {
		return testTypeDTOAux;
	}

	public void setTestTypeDTOAux(TestTypeDTO testTypeDTOAux) {
		this.testTypeDTOAux = testTypeDTOAux;
	}

	public TestMethodTypeDTO getTestMethodTypeDTOAux() {
		return testMethodTypeDTOAux;
	}

	public void setTestMethodTypeDTOAux(TestMethodTypeDTO testMethodTypeDTOAux) {
		this.testMethodTypeDTOAux = testMethodTypeDTOAux;
	}
	
}
