package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.validator.ValidationUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Implementation;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.WsdlDTO;



public class ImplementationMB implements Validation {
	public static final String GENERAL_ARTIFACT_REFERENCE_PATH = "Solution\\Implementation\\General Artifact";
	public static final String USER_INTERFACE_REFERENCE_PATH = "Solution\\Implementation\\User Interface";
	public static final String SOURCE_CODE_REFERENCE_PATH = "Solution\\Implementation\\Source Code";
	public static final String WSDL_REFERENCE_PATH = "Solution\\Implementation\\WSDL";
	
	private AssetMB assetMB;
	
	private DesignPatternDTO designPatternDTOAux;
	private ProgrammingLanguageDTO programmingLanguageDTOAux;
	
	private ArtifactMB artifactMB;
	private ArtifactMB wsdlMB;
	private ArtifactMB userInterfaceMB;
	private ArtifactMB sourceCodeMB;
	
	public ImplementationMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		this.initMB();
	}
	
	private void initMB() {
		designPatternDTOAux = new DesignPatternDTO();
		programmingLanguageDTOAux = new ProgrammingLanguageDTO();
		
		artifactMB = new ArtifactMB(this.assetMB,
												getDTO().getArtifactDTOs(),
												ArtifactType.GENERAL);
		wsdlMB = new ArtifactMB(this.assetMB,
                        				getDTO().getWsdlDTOs(),
                        				ArtifactType.WSDL);
		userInterfaceMB = new ArtifactMB(this.assetMB,
                        				getDTO().getUserInterfaceDTOs(),
                        				ArtifactType.USER_INTERFACE);
		sourceCodeMB = new ArtifactMB(this.assetMB,
                        				getDTO().getSourceCodeDTOs(),
                        				ArtifactType.SOURCE_CODE);
	}
	
	@Override
	public String validate() {
		if (programmingLanguageDTOAux != null) {
			return "Select the programming language then click in Add.";
		}
		for (SourceCodeDTO sourceCodeDTO : getDTO().getSourceCodeDTOs()) {
			if (!ValidationUtil.validate(sourceCodeDTO) ||
					sourceCodeDTO.getSourceCodeTypeDTO() == null) {
				return "Enter all blank fields of source code artifacts.";
			}
		}
		for (UserInterfaceDTO uiDTO : getDTO().getUserInterfaceDTOs()) {
			if (!ValidationUtil.validate(uiDTO) ||
					uiDTO.getUserInterfaceTypeDTO() == null) {
				return "Enter all blank fields of user interface artifacts.";
			}
		}
		for (WsdlDTO wsdlDTO : getDTO().getWsdlDTOs()) {
			if (!ValidationUtil.validate(wsdlDTO)) {
				return "Enter all blank fields of wsdl artifacts.";
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
		for (Artifactable artifactDTO : getDTO().getUserInterfaceDTOs()) {
			artifactDTO.setLocation(USER_INTERFACE_REFERENCE_PATH);
		}
		for (Artifactable wsdlDTO : getDTO().getWsdlDTOs()) {
			wsdlDTO.setLocation(WSDL_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			artifactDTO.setLocation(GENERAL_ARTIFACT_REFERENCE_PATH);
		}
		artifacts.addAll(getDTO().getSourceCodeDTOs());
		artifacts.addAll(getDTO().getUserInterfaceDTOs());
		artifacts.addAll(getDTO().getWsdlDTOs());
		artifacts.addAll(getDTO().getArtifactDTOs());
		return artifacts;
	}
	
	public Boolean getHasImplementation() {
		return getDTO().getArtifactDTOs().size() > 0 ||
   				getDTO().getWsdlDTOs().size() > 0 ||
   				getDTO().getDesignPatternDTOs().size() > 0 ||
   				getDTO().getProgrammingLanguageDTOs().size() > 0 ||
   				getDTO().getSourceCodeDTOs().size() > 0 ||
   				getDTO().getUserInterfaceDTOs().size() > 0;
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
	
	public Implementation getDTO() {
		return this.assetMB.getAsset().getSolution().getImplementation();
	}
	
	public ArtifactMB getArtifactMB() {
		return artifactMB;
	}

	public void setArtifactMB(ArtifactMB artifactMB) {
		this.artifactMB = artifactMB;
	}
	
	public ArtifactMB getWsdlMB() {
		return wsdlMB;
	}

	public void setWsdlMB(ArtifactMB wsdlMB) {
		this.wsdlMB = wsdlMB;
	}

	public ArtifactMB getUserInterfaceMB() {
		return userInterfaceMB;
	}

	public void setUserInterfaceMB(ArtifactMB userInterfaceMB) {
		this.userInterfaceMB = userInterfaceMB;
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

	public void setProgrammingLanguageDTOAux(ProgrammingLanguageDTO programmingLanguageDTOAux) {
		this.programmingLanguageDTOAux = programmingLanguageDTOAux;
	}

}
