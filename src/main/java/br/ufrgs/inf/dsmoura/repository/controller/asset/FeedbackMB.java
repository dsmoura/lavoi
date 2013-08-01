package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.search.SimpleSearchMB;
import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.AssetDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.FeedbackDTO;

@KeepAlive
public class FeedbackMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private FeedbackDTO feedbackDTO;
	private List<FeedbackDTO> feedbackDTOs;
	Asset assetToFeedback;
	
	final static Log logger = LogFactory.getLog(FeedbackMB.class);

	public FeedbackMB() {
	}
	
	public String openAssetToFeedback() {
		/* Prepare the feedback */
		String pkParam = JSFUtil.getRequestParameter("assetPkToFeedback");
		assetToFeedback = AssetDAO.getInstance().findUniqueByPk(Integer.valueOf(pkParam));
		/* Find the feedback for the user */
		feedbackDTO = null;
		for (FeedbackDTO f : assetToFeedback.getFeedbackDTOs()) {
			if (f.getFeedbackUserDTO().getUsername().equalsIgnoreCase( JSFUtil.getLoggedUserDTO().getUsername() )) {
				feedbackDTO = f;
				break;
			}
		}
		if (feedbackDTO == null) {
			throw new RuntimeException("feedbackDTO not found.");
		}
		return NavigationMB.FEEDBACK_ASSET;
	}
	
	public String saveFeedback() {
		feedbackDTO.setHasFeedback(true);
		feedbackDTO.setDate(Calendar.getInstance());
		
		AssetDAO.getInstance().update(assetToFeedback);
		
		this.refreshListAssetsToFeedback();
		
		if (this.getIsFeedbackOnCertification()) {
			SimpleSearchMB simpleSearchMB = JSFUtil.findBean("simpleSearchMB");
			return simpleSearchMB.searchAssetsForCertificationList(); 
		}
		else {
			return NavigationMB.FEEDBACK_LIST;
		}
	}
	
	public Boolean getIsQualityInUseMandatory() {
		return ! this.feedbackDTO.getIsForCertification();
	}
	
	public Boolean getIsSoftwareProductQualityMandatory() {
		return this.feedbackDTO.getIsForCertification();
	}
	
	public Boolean getIsFeedbackOnConsumption() {
		return ! this.feedbackDTO.getIsForCertification();
	}
	
	public Boolean getIsFeedbackOnCertification() {
		return this.feedbackDTO.getIsForCertification();
	}
	
	public String openListAssetsToFeedback() {
		this.refreshListAssetsToFeedback();
		return NavigationMB.FEEDBACK_LIST;
	}
	
	public String getAverageScore() {
		Float f = AssetDAO.getInstance().evaluateQuality(this.feedbackDTO);
		if (f == null || f.isNaN()) {
			return "-";
		}
		return FieldsUtil.normalizeAverageScore(f);
	}
	
	private void refreshListAssetsToFeedback() {
		this.feedbackDTOs = AssetDAO.getInstance().listConsumptionFeedbacksByUser(JSFUtil.getLoggedUserDTO());
	}
	
	public FeedbackDTO getFeedbackDTO() {
		return feedbackDTO;
	}

	public void setFeedbackDTO(FeedbackDTO feedbackDTO) {
		this.feedbackDTO = feedbackDTO;
	}

	public List<FeedbackDTO> getFeedbackDTOs() {
		if (this.feedbackDTOs == null) {
			this.refreshListAssetsToFeedback();
		}
		return feedbackDTOs;
	}

	public void setAssetsToFeedback(List<FeedbackDTO> feedbackDTOs) {
		this.feedbackDTOs = feedbackDTOs;
	}

	public Asset getAssetToFeedback() {
		return assetToFeedback;
	}

	public void setAssetToFeedback(Asset assetToFeedback) {
		this.assetToFeedback = assetToFeedback;
	}
	
}
