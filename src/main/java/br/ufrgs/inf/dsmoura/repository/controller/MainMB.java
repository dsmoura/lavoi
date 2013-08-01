package br.ufrgs.inf.dsmoura.repository.controller;

import java.io.Serializable;
import java.util.Calendar;


import org.ajax4jsf.model.KeepAlive;

import br.ufrgs.inf.dsmoura.repository.controller.asset.NavigationMB;
import br.ufrgs.inf.dsmoura.repository.controller.search.SearchMB;
import br.ufrgs.inf.dsmoura.repository.controller.util.EmailUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.GenericDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FeedbackMessageDTO;


@KeepAlive
public class MainMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String feedbackMessage;
	
	public String openMain() {
		/* refresh tag cloud in main */
		TagsMB tagsMB = JSFUtil.findBean("tagsMB");
		tagsMB.refreshMainTags();
		/* refresh assets lists */
		SearchMB searchMB = JSFUtil.findBean("searchMB");
		searchMB.refreshBestScoredAssetsList();	//TODO otimizar o refresh da primeira entrada, porque no get ja faz isso pela primeira vez
		searchMB.refreshMostReusedAssetsList();
		searchMB.refreshLastCertifiedAssetsList();
		searchMB.refreshLastPublishedAssetsList();
		searchMB.refreshPublishedAssetsNumber();
		return NavigationMB.MAIN;
	}
	
	public String openSendFeedback() {
		this.feedbackMessage = "";
		return NavigationMB.SEND_FEEDBACK_MESSAGE;
	}
	
	public void sendFeedbackMessage() {
		if (feedbackMessage != null &&
				feedbackMessage.trim().length() > 0) {
   		String FEEDBACK_EMAIL = TypesDAO.getInstance().getSystemProperty(SystemPropertyEnum.FEEDBACK_EMAIL.getKey()).getValue();
   		/* Save in database */
   		FeedbackMessageDTO feedbackMessageDTO = new FeedbackMessageDTO();
   		feedbackMessageDTO.setMessage(feedbackMessage);
   		feedbackMessageDTO.setUsername(JSFUtil.getLoggedUserDTO().getUsername());
   		feedbackMessageDTO.setEmail(JSFUtil.getLoggedUserDTO().getEmail());
   		feedbackMessageDTO.setCurrentIp(JSFUtil.getCurrentIP());
   		feedbackMessageDTO.setDate(Calendar.getInstance());
   		GenericDAO.getInstance().insert(feedbackMessageDTO);
   		/* Send email */
   		String emailMessage = "Feedback from user:" +
   							"\n\nFull Name: " + JSFUtil.getLoggedUserDTO().getName() +
   							"\nUsername: " + JSFUtil.getLoggedUserDTO().getUsername() +
   							"\nEmail: " + JSFUtil.getLoggedUserDTO().getEmail() +
   							"\nCurrent User IP: " + JSFUtil.getCurrentIP() +
   							"\n\nMessage:\n" +
   							feedbackMessage + "\n";
   		EmailUtil.sendMail(FEEDBACK_EMAIL,
         							"Feedback Message",
         							emailMessage);
		}
		feedbackMessage = "";
	}

	public String getFeedbackMessage() {
		return feedbackMessage;
	}
	public void setFeedbackMessage(String feedbackMessage) {
		this.feedbackMessage = feedbackMessage;
	}
	
}