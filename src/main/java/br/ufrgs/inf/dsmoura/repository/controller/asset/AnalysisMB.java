package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.validator.ValidationUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.Analysis;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.InterfaceSpecDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UseCaseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;



public class AnalysisMB implements Validation {
	public static final String GENERAL_ARTIFACT_REFERENCE_PATH = "Solution\\Analisys\\General Artifact";
	public static final String USER_INTERFACE_REFERENCE_PATH = "Solution\\Analisys\\User Interface";
	public static final String USE_CASE_REFERENCE_PATH = "Solution\\Analisys\\Use Case";
	
	private AssetMB assetMB;
	
	private ArtifactMB artifactMB;
	private ArtifactMB useCaseMB;
	private ArtifactMB userInterfaceMB;
	
	public AnalysisMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		
		artifactMB = new ArtifactMB(this.assetMB,
                        				getDTO().getArtifactDTOs(),
                        				ArtifactType.GENERAL);
		useCaseMB = new ArtifactMB(this.assetMB,
                        				getDTO().getUseCaseDTOs(),
                        				ArtifactType.USE_CASE);
		userInterfaceMB = new ArtifactMB(this.assetMB,
                              				getDTO().getUserInterfaceDTOs(),
                              				ArtifactType.USER_INTERFACE);
	}
	
	@Override
	public String validate() {
		String message = "Enter all blank fields of interface specs and operations.";
		for (InterfaceSpecDTO intSpecDTO : getDTO().getInterfaceSpecDTOs()) {
			if (intSpecDTO.getName() == null || intSpecDTO.getName().isEmpty()) {
				return message;
			}
			for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
				if (operationDTO.getName() == null || operationDTO.getName().isEmpty()) {
					return message;
				}
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
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			if (!ValidationUtil.validate(artifactDTO)) {
				return "Enter all blank fields of general artifacts.";
			}
		}
		return "";
	}
	
	public List<Artifactable> getArtifacts() {
		List<Artifactable> artifacts = new ArrayList<Artifactable>();
		for (Artifactable artifactDTO : getDTO().getUseCaseDTOs()) {
			artifactDTO.setLocation(USE_CASE_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getUserInterfaceDTOs()) {
			artifactDTO.setLocation(USER_INTERFACE_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			artifactDTO.setLocation(GENERAL_ARTIFACT_REFERENCE_PATH);
		}
		artifacts.addAll(getDTO().getUseCaseDTOs());
		artifacts.addAll(getDTO().getUserInterfaceDTOs());
		artifacts.addAll(getDTO().getArtifactDTOs());
		return artifacts;
	}
	
	public Boolean getHasAnalysis() {
		return getDTO().getArtifactDTOs().size() > 0 ||
   				getDTO().getInterfaceSpecDTOs().size() > 0 ||
   				getDTO().getUserInterfaceDTOs().size() > 0 ||
   				getDTO().getUseCaseDTOs().size() > 0;
	}
	
	/* INTERFACE SPEC */
	
	public String addNewInterfaceSpec() {
		InterfaceSpecDTO interfaceSpecDTO = new InterfaceSpecDTO();
		interfaceSpecDTO.getOperationDTOs().add( new OperationDTO() );
		getDTO().getInterfaceSpecDTOs().add( interfaceSpecDTO );
		return "";
	}
	
	public String addInterfaceSpec() {
		Long interfaceSpecRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("interfaceSpecRandomIDToAdd") );
		for (InterfaceSpecDTO interfaceSpecDTO : getDTO().getInterfaceSpecDTOs()) {
			if (interfaceSpecDTO.getRandomID().equals(interfaceSpecRandomIDToAdd)) {
				int index = getDTO().getInterfaceSpecDTOs().indexOf(interfaceSpecDTO);
				InterfaceSpecDTO intSpecDTO = new InterfaceSpecDTO();
				intSpecDTO.getOperationDTOs().add( new OperationDTO() );
				getDTO().getInterfaceSpecDTOs().add( index + 1, intSpecDTO );
				break;
			}
		}
		return "";
	}
	
	public String removeInterfaceSpec() {
		Long interfaceSpecRandomIDToRemove = Long.valueOf( JSFUtil.getRequestParameter("interfaceSpecRandomIDToRemove") );
		for (InterfaceSpecDTO intSpecDTO : getDTO().getInterfaceSpecDTOs()) {
			if (intSpecDTO.getRandomID().equals(interfaceSpecRandomIDToRemove)) {
				getDTO().getInterfaceSpecDTOs().remove(intSpecDTO);
				break;
			}
		}
		return "";
	}
	
	public String addOperation() {
		Long interfaceSpecRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("interfaceSpecRandomIDToAdd") );
		Long operationRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("operationRandomIDToAdd") );
		for (InterfaceSpecDTO intSpecDTO : getDTO().getInterfaceSpecDTOs()) {
			if (intSpecDTO.getRandomID().equals(interfaceSpecRandomIDToAdd)) {
				for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
					if (operationDTO.getRandomID().equals( operationRandomIDToAdd )) {
						int index = intSpecDTO.getOperationDTOs().indexOf(operationDTO);
						intSpecDTO.getOperationDTOs().add(index + 1, new OperationDTO() );
						break;
					}
				}
			}
		}
		return "";
	}
	
	public String removeOperation() {
		Long interfaceSpecRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("interfaceSpecRandomIDToRemove") );
		Long operationRandomIDToRemove = Long.valueOf( JSFUtil.getRequestParameter("operationRandomIDToRemove") );
		outFor:
		for (InterfaceSpecDTO intSpecDTO : getDTO().getInterfaceSpecDTOs()) {
			if (intSpecDTO.getRandomID().equals(interfaceSpecRandomIDToAdd)) {
				for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
					if (operationDTO.getRandomID().equals( operationRandomIDToRemove )) {
						intSpecDTO.getOperationDTOs().remove(operationDTO);
						if (intSpecDTO.getOperationDTOs().isEmpty()) {
							intSpecDTO.getOperationDTOs().add( new OperationDTO() );
						}
						break outFor;
					}
				}
			}
		}
		return "";
	}
	
	public Analysis getDTO() {
		return this.assetMB.getAsset().getSolution().getAnalysis();
	}

	public ArtifactMB getArtifactMB() {
		return artifactMB;
	}

	public void setArtifactMB(ArtifactMB artifactMB) {
		this.artifactMB = artifactMB;
	}
	
	public ArtifactMB getUseCaseMB() {
		return useCaseMB;
	}

	public void setUseCaseMB(ArtifactMB useCaseMB) {
		this.useCaseMB = useCaseMB;
	}

	public ArtifactMB getUserInterfaceMB() {
		return userInterfaceMB;
	}

	public void setUserInterfaceMB(ArtifactMB userInterfaceMB) {
		this.userInterfaceMB = userInterfaceMB;
	}
	
}
