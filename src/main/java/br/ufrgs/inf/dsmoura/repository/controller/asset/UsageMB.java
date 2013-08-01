package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.validator.ValidationUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.AssetDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.Usage;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserCommentDTO;

public class UsageMB implements Validation {
	
	public static final String GENERAL_ARTIFACT_REFERENCE_PATH = "Usage\\General Artifact";
	
	private AssetMB assetMB;
	private ArtifactMB artifactMB;
	private String userCommentAux;
	
	public UsageMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		this.initMB();
	}
	
	private void initMB() {
		artifactMB = new ArtifactMB(this.assetMB,
                        				getDTO().getArtifactDTOs(),
                        				ArtifactType.GENERAL);
	}
	
	@Override
	public String validate() {
		if (StringUtils.isBlank( getDTO().getDescription() )) {
			JSFUtil.addErrorMessage("usageDescriptionID", "Enter the Usage Description.");
			return "Enter the Usage Description.";
		}
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			if (!ValidationUtil.validate(artifactDTO)) {
				return "Enter all blank fields of general artifacts.";
			}
		}
		return "";
	}
	
	public String addUserComment() {
		if (!JSFUtil.isLoggedUser()) {
			JSFUtil.addErrorMessage("userCommentID", "Login is needed to add comments.");
		}
		if ((userCommentAux != null) && (userCommentAux.trim().length() > 0)) {
			/* Update Asset */
			UserCommentDTO userCommentDTO = new UserCommentDTO();
			userCommentDTO.setComment(userCommentAux);
			userCommentDTO.setDate(Calendar.getInstance());
			userCommentDTO.setUserDTO(JSFUtil.getLoggedUserDTO());
			getDTO().getUserCommentDTOs().add(userCommentDTO);
			AssetDAO.getInstance().update(assetMB.getAsset());
			/* Open Asset */
			assetMB.openAsset(assetMB.getAsset().getAssetPk());
			/* Inform the Author about the comment */
			if ( ! assetMB.getIsAuthor() ) {
				NotificationMB.sendCommentNotificationToAuthors(assetMB.getAsset(), userCommentDTO);
			}
			/* Reset comment */
			userCommentAux = "";
		}
		else {
			JSFUtil.addErrorMessage("userCommentID", "Enter the comment.");
		}
		return "";
	}
	
	public List<Artifactable> getArtifacts() {
		List<Artifactable> artifacts = new ArrayList<Artifactable>();
		for (Artifactable artifactDTO : getDTO().getArtifactDTOs()) {
			artifactDTO.setLocation(GENERAL_ARTIFACT_REFERENCE_PATH);
		}
		artifacts.addAll(getDTO().getArtifactDTOs());
		return artifacts;
	}
	
	public Date getAuthorshipDateTime() {
		if (getDTO().getAuthorshipDate() != null) {
			return getDTO().getAuthorshipDate().getTime();
		}
		return null;
	}
	
	public Usage getDTO() {
		return this.assetMB.getAsset().getUsage();
	}
	
	public ArtifactMB getArtifactMB() {
		return artifactMB;
	}

	public void setArtifactMB(ArtifactMB artifactMB) {
		this.artifactMB = artifactMB;
	}

	public String getUserCommentAux() {
		return userCommentAux;
	}

	public void setUserCommentAux(String userCommentAux) {
		this.userCommentAux = userCommentAux;
	}

}
