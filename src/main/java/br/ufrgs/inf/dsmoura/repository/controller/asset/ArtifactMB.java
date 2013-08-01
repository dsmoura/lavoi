package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.List;


import org.richfaces.event.UploadEvent;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDependencyDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UseCaseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.WsdlDTO;


public class ArtifactMB {
	
	private AssetMB assetMB;
	private List<Artifactable> artifacts;
	private ArtifactType artifactType;
	
	@SuppressWarnings("unchecked")
	public ArtifactMB(AssetMB assetMB, List<?> artifacts, ArtifactType artifactType) {
		this.artifacts = (List<Artifactable>) artifacts;
		this.assetMB = assetMB;
		this.artifactType = artifactType;
	}
	
	public void uploadArtifact(UploadEvent event) throws Exception {
		Long artifactRandomIDToUpload = Long.parseLong(JSFUtil.getRequestParameter("artifactRandomID"));
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomIDToUpload)) {
				artifactDTO.setName( getOnlyFileName(event.getUploadItem().getFileName()) );
				artifactDTO.setReference(AssetMB.REFERENCE_MODEL);
				artifactDTO.setType(event.getUploadItem().getContentType());
				artifactDTO.setFile(event.getUploadItem().getData());
				break;
			}
		}
	}
	
	public void addArtifact() {
		Artifactable artifactable = this.getNewArtifactable();
		artifactable.setId(this.assetMB.getNextArtifactID()+"");
		artifactable.setType(AssetMB.REFERENCE_ARTIFACT);
		artifactable.setSoftwareLicenseDTO(assetMB.getAsset().getSoftwareLicenseDTO());
		artifacts.add( artifactable );
	}
	
	public String addArtifactOrdered() {
		Long artifactRandomID = Long.parseLong(JSFUtil.getRequestParameter("artifactRandomID"));
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomID)) {
				int index = artifacts.indexOf(artifactDTO);
				Artifactable artifactable = this.getNewArtifactable();
				artifactable.setId(this.assetMB.getNextArtifactID()+"");
				artifactable.setType(AssetMB.REFERENCE_ARTIFACT);
				artifacts.add( index + 1, artifactable );
				break;
			}
		}
		return "";
	}
	
	public String removeArtifact() {
		Long artifactRandomID = Long.parseLong(JSFUtil.getRequestParameter("artifactRandomID"));
		outFor:
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomID)) {
				for (Artifactable a : assetMB.getAssetArtifacts()) {
					for (ArtifactDependencyDTO d : a.getArtifactDependencyDTOs()) {
						if (artifactDTO.getId().equalsIgnoreCase( d.getArtifactID() )) {
							JSFUtil.addErrorMessage(this.getArtifactMBID(),
																"The artifact (ID " +
																artifactDTO.getId() +
																") cannot be removed because it has dependencies.");
							break outFor;
						}
					}
				}
				artifacts.remove(artifactDTO);
				break;
			}
		}
		return "";
	}
	
	public String addArtifactDependency() {
		Long artifactRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("artifactRandomID") );
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomIDToAdd)) {
				artifactDTO.getArtifactDependencyDTOs().add(new ArtifactDependencyDTO());
				break;
			}
		}
		return "";
	}
	
//	http://www.coderanch.com/t/212276/JSF/java/dataTable-new-rows
	
	public String addGeneralArtifactDependencyOrdered() {
		Long artifactRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("artifactRandomID") );
		Long artifactDependencyRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("artifactDependencyRandomID") );
		outFor:
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomIDToAdd)) {
				for (ArtifactDependencyDTO artifactDependencyDTO : artifactDTO.getArtifactDependencyDTOs()) {
					if (artifactDependencyDTO.getRandomID().equals( artifactDependencyRandomIDToAdd )) {
						int index = artifactDTO.getArtifactDependencyDTOs().indexOf(artifactDependencyDTO);
						artifactDTO.getArtifactDependencyDTOs().add(index + 1, new ArtifactDependencyDTO() );
						break outFor;
					}
				}
			}
		}
		return "";
	}
	
	public String removeGeneralArtifactDependency() {
		Long artifactRandomIDToRemove = Long.valueOf( JSFUtil.getRequestParameter("artifactRandomID") );
		Long artifactDependencyRandomIDToRemove = Long.valueOf( JSFUtil.getRequestParameter("artifactDependencyRandomID") );
		outFor:
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomIDToRemove)) {
				for (ArtifactDependencyDTO artifactDependencyDTO : artifactDTO.getArtifactDependencyDTOs()) {
					if (artifactDependencyDTO.getRandomID().equals( artifactDependencyRandomIDToRemove )) {
						artifactDTO.getArtifactDependencyDTOs().remove(artifactDependencyDTO);
						break outFor;
					}
				}
			}
		}
		return "";
	}
	
	public void validateArtifactDependency() {
		for (Artifactable artifactDTO : artifacts) {
			for (ArtifactDependencyDTO artifactDependencyDTO : artifactDTO.getArtifactDependencyDTOs()) {
				if (artifactDependencyDTO.getArtifactID() != null) {
					if (artifactDTO.getId().equalsIgnoreCase( artifactDependencyDTO.getArtifactID() )) {
						artifactDependencyDTO.setArtifactID(null);
						JSFUtil.addErrorMessage(this.getArtifactMBID(),
															"The artifact (ID " +
															artifactDTO.getId() +
															") cannot depend on itself.");
					}
				}
			}
		}
	}
	
	public String downloadArtifact() {
		Long artifactRandomID = Long.parseLong(JSFUtil.getRequestParameter("artifactRandomID"));
		Artifactable artifactDTOToDownload = null;
		for (Artifactable artifactDTO : artifacts) {
			if (artifactDTO.getRandomID().equals(artifactRandomID)) {
				artifactDTOToDownload = artifactDTO;
				break;
			}
		}
		JSFUtil.downloadFile(artifactDTOToDownload.getName(),
									artifactDTOToDownload.getFile(),
									artifactDTOToDownload.getType());
		return "";
	}
	
	public String getArtifactMBID () {
		return "artifactsPanel" + this.hashCode() + "ID";
	}
	
	private Artifactable getNewArtifactable() {
		Artifactable artifactable;
		switch (artifactType) {
   		case GENERAL: {
   			artifactable = new ArtifactDTO();
   			break;
   		}
   		case FUNCTIONAL_REQ: {
   			artifactable = new FunctionalRequirementDTO();
   			break;
   		}
   		case NON_FUNCTIONAL_REQ: {
   			artifactable = new NonFunctionalRequirementDTO();
   			break;
   		}
   		case USE_CASE: {
   			artifactable = new UseCaseDTO();
   			break;
   		}
   		case USER_INTERFACE: {
   			artifactable = new UserInterfaceDTO();
   			break;
   		}
   		case SOURCE_CODE: {
   			artifactable = new SourceCodeDTO();
   			break;
   		}
   		case WSDL: {
   			artifactable = new WsdlDTO();
   			break;
   		}
   		default: {
   			throw new RuntimeException("Invalid artifactType value: " + artifactType);
   		}
		}
		return artifactable;
	}
	
	private String getOnlyFileName(String s) {
		if (s.contains("\\")) {
			int lastIndex = s.lastIndexOf("\\");
			s = s.substring(lastIndex + 1);
		}
		if (s.contains("/")) {
			int lastIndex = s.lastIndexOf("/");
			s = s.substring(lastIndex + 1);
		}
		return s;
	}
	
	public Boolean getContainsArtifacts() {
		return (artifacts != null) &&
					(artifacts.size() > 0); 
	}
	
	public Boolean getHasGeneralArtifacts() {
		return this.artifactType.equals(ArtifactType.GENERAL);
	}
	
	public Boolean getHasUseCaseArtifacts() {
		return this.artifactType.equals(ArtifactType.USE_CASE);
	}
	
	public Boolean getHasUserInterfaceArtifacts() {
		return this.artifactType.equals(ArtifactType.USER_INTERFACE);
	}
	
	public Boolean getHasSourceCodeArtifacts() {
		return this.artifactType.equals(ArtifactType.SOURCE_CODE);
	}
	
	public Boolean getHasFunctionalArtifacts() {
		return this.artifactType.equals(ArtifactType.FUNCTIONAL_REQ);
	}
	
	public Boolean getHasNonFunctionalArtifacts() {
		return this.artifactType.equals(ArtifactType.NON_FUNCTIONAL_REQ);
	}
	
	public Boolean getWsdlArtifacts() {
		return this.artifactType.equals(ArtifactType.WSDL);
	}
	
	public String getArtifactTypeName() {
		return this.artifactType.getName();
	}
	
	public List<Artifactable> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<Artifactable> artifacts) {
		this.artifacts = artifacts;
	}
	
}
