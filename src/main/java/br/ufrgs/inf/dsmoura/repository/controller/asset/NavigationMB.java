package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.io.Serializable;

public class NavigationMB implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String LOGIN = "login";
	public static final String REGISTER_LOGIN = "registerLogin";
	public static final String REGISTERED_LOGIN = "registeredLogin";
	public static final String CHANGED_PASSWORD_LOGIN = "resetedPassword";
	public static final String RESEND_CODE_LOGIN = "resendCodeLogin";
	
	public static final String MAIN = "main";
	
	public static final String SEND_FEEDBACK_MESSAGE = "sendFeedbackMessage";
	public static final String FEEDBACK_MESSAGE_SENT = "feedbackMessageSent";
	
	public static final String NEW_ASSET = "addAsset";
	public static final String EDIT_ASSET = "addAsset";
	public static final String OPEN_ASSET = "openAsset";
	
	public static final String ASSET_REMOVED = "assetRemoved";
	
	public static final String CONSUMED_ASSET = "consumed";
	public static final String CERTIFY_ASSET = "certify";
	
	public static final String ADVANCED_SEARCH = "advancedSearch";
	public static final String SEARCH_ASSETS = "searchAssets";
	
	public static final String FEEDBACK_LIST = "feedbackList";
	public static final String FEEDBACK_ASSET = "feedbackAsset";
	
	public static final String TAGS = "tags";
	
	public static final String PUBLISHED = "published";

//	public static final String ASSETS_PUBLISHED_BY_ME = "myPublishedAssets";
//	public static final String ASSETS_FOR_CERTIFICATION = "assetsForCertification";

}
