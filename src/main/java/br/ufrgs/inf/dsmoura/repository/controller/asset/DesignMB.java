package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.validator.ValidationUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.Design;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InterfaceSpecDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;



public class DesignMB implements Validation {
	public static final String GENERAL_ARTIFACT_REFERENCE_PATH = "Solution\\Design\\General Artifact";
	public static final String USER_INTERFACE_REFERENCE_PATH = "Solution\\Design\\User Interface";
	
	private AssetMB assetMB;
	
	private DesignPatternDTO designPatternDTOAux;
	
	private ArtifactMB artifactMB;
	private ArtifactMB userInterfaceMB;
	
	public DesignMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		this.initMB();
	}
	
	private void initMB() {
		designPatternDTOAux = new DesignPatternDTO();
		
		artifactMB = new ArtifactMB(this.assetMB,
                        				getDTO().getArtifactDTOs(),
                        				ArtifactType.GENERAL);
		userInterfaceMB = new ArtifactMB(this.assetMB,
                              				getDTO().getUserInterfaceDTOs(),
                              				ArtifactType.USER_INTERFACE);
	}
	
	@Override
	public String validate() {
		if (designPatternDTOAux != null) {
			return "Select the design pattern then click in Add.";
		}
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
		for (Artifactable artifactDTO : getDTO().getUserInterfaceDTOs()) {
			artifactDTO.setLocation(USER_INTERFACE_REFERENCE_PATH);
		}
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			artifactDTO.setLocation(GENERAL_ARTIFACT_REFERENCE_PATH);
		}
		artifacts.addAll(getDTO().getUserInterfaceDTOs());
		artifacts.addAll(getDTO().getArtifactDTOs());
		return artifacts;
	}
	
	public Boolean getHasDesign() {
		return getDTO().getArtifactDTOs().size() > 0 ||
				getDTO().getDesignPatternDTOs().size() > 0 ||
				getDTO().getInterfaceSpecDTOs().size() > 0 ||
				getDTO().getUserInterfaceDTOs().size() > 0;
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
	
	public ArtifactMB getArtifactMB() {
		return artifactMB;
	}

	public void setArtifactMB(ArtifactMB artifactMB) {
		this.artifactMB = artifactMB;
	}

	public ArtifactMB getUserInterfaceMB() {
		return userInterfaceMB;
	}

	public void setUserInterfaceMB(ArtifactMB userInterfaceMB) {
		this.userInterfaceMB = userInterfaceMB;
	}

	public Design getDTO() {
		return this.assetMB.getAsset().getSolution().getDesign();
	}

	public DesignPatternDTO getDesignPatternDTOAux() {
		return designPatternDTOAux;
	}

	public void setDesignPatternDTOAux(DesignPatternDTO designPatternDTOAux) {
		this.designPatternDTOAux = designPatternDTOAux;
	}
	
}
