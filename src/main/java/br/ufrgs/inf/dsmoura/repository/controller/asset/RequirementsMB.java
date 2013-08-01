package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.validator.ValidationUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InternationalizationTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationalSystemTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Requirements;
import br.ufrgs.inf.dsmoura.repository.model.entity.UseCaseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;


public class RequirementsMB implements Validation {
	
	public static final String FUNCTIONAL_REQ_REFERENCE_PATH = "Solution\\Requirements\\Functional Requirements";
	public static final String USER_INTERFACE_REFERENCE_PATH = "Solution\\Requirements\\User Interface";
	public static final String USE_CASE_REFERENCE_PATH = "Solution\\Requirements\\Use Case";
	public static final String NON_FUNCTIONAL_REQ_REFERENCE_PATH = "Solution\\Requirements\\Non-Functional Requirements";
	
	private AssetMB assetMB;
	
	private InternationalizationTypeDTO internationalizationTypeDTOAux;
	private OperationalSystemTypeDTO operationalSystemTypeDTOAux;
	
	private ArtifactMB functionalRequirementMB;
	private ArtifactMB useCaseMB;
	private ArtifactMB userInterfaceMB;
	private ArtifactMB nonFunctionalRequirementMB;
	
	public RequirementsMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		initMB();
	}
	
	private void initMB() {
		internationalizationTypeDTOAux = new InternationalizationTypeDTO();
		operationalSystemTypeDTOAux = new OperationalSystemTypeDTO();
		
		functionalRequirementMB = new ArtifactMB(this.assetMB,
                                    				getDTO().getFunctionalRequirementDTOs(),
                                    				ArtifactType.FUNCTIONAL_REQ);
		useCaseMB = new ArtifactMB(this.assetMB,
                        				getDTO().getUseCaseDTOs(),
                        				ArtifactType.USE_CASE);
		userInterfaceMB = new ArtifactMB(this.assetMB,
                              				getDTO().getUserInterfaceDTOs(),
                              				ArtifactType.USER_INTERFACE);
		nonFunctionalRequirementMB = new ArtifactMB(this.assetMB,
                                       				getDTO().getNonFunctionalRequirementDTOs(),
                                       				ArtifactType.NON_FUNCTIONAL_REQ);
	}

	public Requirements getDTO() {
		return this.assetMB.getAsset().getSolution().getRequirements();
	}
	
	@Override
	public String validate() {
		if (internationalizationTypeDTOAux != null) {
			return "Select the internationalization language then click in Add.";
		}
		if (operationalSystemTypeDTOAux != null) {
			return "Select the operational system then click in Add.";
		}
		for (FunctionalRequirementDTO functionalRequirementDTO : getDTO().getFunctionalRequirementDTOs()) {
			if (!ValidationUtil.validate(functionalRequirementDTO) ||
					functionalRequirementDTO.getFunctionalRequirementTypeDTO() == null) {
				return "Enter all blank fields of functional requirements.";
			}
		}
		for (UseCaseDTO useCaseDTO : getDTO().getUseCaseDTOs()) {
			if (!ValidationUtil.validate(useCaseDTO)) {
				return "Enter all blank fields of use cases.";
			}
		}
		for (UserInterfaceDTO uiDTO : getDTO().getUserInterfaceDTOs()) {
			if (!ValidationUtil.validate(uiDTO) ||
					uiDTO.getUserInterfaceTypeDTO() == null) {
				return "Enter all blank fields of user interface artifacts.";
			}
		}
		for (NonFunctionalRequirementDTO nonFunctionalRequirementDTO : getDTO().getNonFunctionalRequirementDTOs()) {
			if (!ValidationUtil.validate(nonFunctionalRequirementDTO) ||
					nonFunctionalRequirementDTO.getNonFunctionalRequirementTypeDTO() == null) {
				return "Enter all blank fields of non-functional requirements.";
			}
		}
		return "";
	}
	
	public List<Artifactable> getArtifacts() {
		List<Artifactable> artifacts = new ArrayList<Artifactable>();
		for (Artifactable artifactDTO : getDTO().getFunctionalRequirementDTOs()) {
			artifactDTO.setLocation(FUNCTIONAL_REQ_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getUseCaseDTOs()) {
			artifactDTO.setLocation(USE_CASE_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getUserInterfaceDTOs()) {
			artifactDTO.setLocation(USER_INTERFACE_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getNonFunctionalRequirementDTOs()) {
			artifactDTO.setLocation(NON_FUNCTIONAL_REQ_REFERENCE_PATH);
		}
		artifacts.addAll(getDTO().getFunctionalRequirementDTOs());
		artifacts.addAll(getDTO().getUserInterfaceDTOs());
		artifacts.addAll(getDTO().getUseCaseDTOs());
		artifacts.addAll(getDTO().getNonFunctionalRequirementDTOs());
		return artifacts;
	}
	
	public Boolean getHasRequirements() {
		return getDTO().getFunctionalRequirementDTOs().size() > 0 ||
   				getDTO().getInternationalizationTypeDTOs().size() > 0 ||
   				getDTO().getNonFunctionalRequirementDTOs().size() > 0 ||
   				getDTO().getOperationalSystemTypeDTOs().size() > 0 ||
   				getDTO().getUserInterfaceDTOs().size() > 0 ||
   				getDTO().getUseCaseDTOs().size() > 0;
	}
	
	/* INTERNATIONALIZATION */
	public String addInternationalization() {
		if ((internationalizationTypeDTOAux != null) && (internationalizationTypeDTOAux.getInternationalizationtypePk() != null)) {
			if ( ! getDTO().getInternationalizationTypeDTOs().contains( internationalizationTypeDTOAux ) ) {
				getDTO().getInternationalizationTypeDTOs().add( internationalizationTypeDTOAux );
			}
			internationalizationTypeDTOAux = new InternationalizationTypeDTO();
		}
		return "";
	}
	
	public String removeInternationalization() {
		int internationalizationTypePkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("internationalizationTypePkToRemove") );
		for (InternationalizationTypeDTO internationalizationTypeDTO : getDTO().getInternationalizationTypeDTOs()) {
			if (internationalizationTypeDTO.getInternationalizationtypePk() == internationalizationTypePkToRemove) {
				getDTO().getInternationalizationTypeDTOs().remove( internationalizationTypeDTO );
				break;
			}
		}
		return "";
	}
	
	/* OPERATIONAL SYSTEM */
	public String addOperationalSystem() {
		if ((operationalSystemTypeDTOAux != null) && (operationalSystemTypeDTOAux.getOperationalsystemtypePk() != null)) {
			if ( ! getDTO().getOperationalSystemTypeDTOs().contains( operationalSystemTypeDTOAux ) ) {
				getDTO().getOperationalSystemTypeDTOs().add( operationalSystemTypeDTOAux );
			}
			operationalSystemTypeDTOAux = new OperationalSystemTypeDTO();
		}
		return "";
	}
	
	public String removeOperationalSystem() {
		int operationalSystemTypePkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("operationalSystemTypePkToRemove") );
		for (OperationalSystemTypeDTO operationalSystemTypeDTO : getDTO().getOperationalSystemTypeDTOs()) {
			if (operationalSystemTypeDTO.getOperationalsystemtypePk() == operationalSystemTypePkToRemove) {
				getDTO().getOperationalSystemTypeDTOs().remove( operationalSystemTypeDTO );
				break;
			}
		}
		return "";
	}
	
	public ArtifactMB getFunctionalRequirementMB() {
		return functionalRequirementMB;
	}

	public void setFunctionalRequirementMB(ArtifactMB functionalRequirementMB) {
		this.functionalRequirementMB = functionalRequirementMB;
	}
	
	public ArtifactMB getUserInterfaceMB() {
		return userInterfaceMB;
	}

	public void setUserInterfaceMB(ArtifactMB userInterfaceMB) {
		this.userInterfaceMB = userInterfaceMB;
	}
	
	public ArtifactMB getUseCaseMB() {
		return useCaseMB;
	}

	public void setUseCaseMB(ArtifactMB useCaseMB) {
		this.useCaseMB = useCaseMB;
	}

	public ArtifactMB getNonFunctionalRequirementMB() {
		return nonFunctionalRequirementMB;
	}

	public void setNonFunctionalRequirementMB(ArtifactMB nonFunctionalRequirementMB) {
		this.nonFunctionalRequirementMB = nonFunctionalRequirementMB;
	}

	public InternationalizationTypeDTO getInternationalizationTypeDTOAux() {
		return internationalizationTypeDTOAux;
	}

	public void setInternationalizationTypeDTOAux(
			InternationalizationTypeDTO internationalizationTypeDTOAux) {
		this.internationalizationTypeDTOAux = internationalizationTypeDTOAux;
	}

	public OperationalSystemTypeDTO getOperationalSystemTypeDTOAux() {
		return operationalSystemTypeDTOAux;
	}

	public void setOperationalSystemTypeDTOAux(
			OperationalSystemTypeDTO operationalSystemTypeDTOAux) {
		this.operationalSystemTypeDTOAux = operationalSystemTypeDTOAux;
	}

}
