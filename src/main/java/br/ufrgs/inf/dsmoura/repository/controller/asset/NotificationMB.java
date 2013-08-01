package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.SystemPropertyEnum;
import br.ufrgs.inf.dsmoura.repository.controller.login.LDAPUserAuthentication;
import br.ufrgs.inf.dsmoura.repository.controller.login.UserAuthentication;
import br.ufrgs.inf.dsmoura.repository.controller.util.EmailUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.AssetDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.UserDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.AdjustmentDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.ConsumptionDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAsset;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserCommentDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;


public class NotificationMB {
	
	static final Log logger = LogFactory.getLog(NotificationMB.class);
	
	public static void sendNotificationsToRelatedAssets(Asset asset) {
		HashSet<UserDTO> previousVersionsConsumers = new HashSet<UserDTO>();
		for (RelatedAsset relatedAsset : asset.getRelatedAssets()) {
			if (relatedAsset.getRelatedAssetTypeDTO().getName().equalsIgnoreCase("Previous Version")) {
				Asset relatedAssetDTO = AssetDAO.getInstance().findAssetByIDVersion(relatedAsset.getId(),
																											relatedAsset.getVersion());
				if (relatedAssetDTO == null) {
					logger.info("Related asset not found in database. ID = " +
										relatedAsset.getId() +
										" version = " +
										relatedAsset.getVersion());
					continue;
				}
				for (ConsumptionDTO consumptionDTO : relatedAssetDTO.getUsage().getConsumptionDTOs()) {
					previousVersionsConsumers.add(consumptionDTO.getConsumerUserDTO());
				}
			}
		}
		if (previousVersionsConsumers.size() == 0) {
			return;
		}
		String subject = "Asset with new version: " + asset.getName();
		String msg = "Software Reuse Repository message:\n\n" +
         				"There is a new version of an asset which you have consumed.\n\n" +
         				"Asset:\n" +
         				"ID: " + asset.getId() + "\n" +
         				"Name: " + asset.getName() + "\n" + 
         				"Version: " + asset.getVersion() + "\n" +
         				"State: " + asset.getState() + "\n\n" +
         				"Thanks.";
		List<String> toList = new ArrayList<String>();
		for (UserDTO consumerUserDTO : previousVersionsConsumers) {
			toList.add( consumerUserDTO.getEmail() );
		}
		if (toList.size() > 0) {
			EmailUtil.sendMail(toList, subject, msg);
		}
	}
	
	public static void sendNotificationsAboutAdjustment(Asset asset) {
		if (asset.getUsage().getConsumptionDTOs().size() == 0) {
			return;
		}
		AdjustmentDTO adjustmentDTO = asset.getUsage().getAdjustmentDTOs().get(asset.getUsage().getAdjustmentDTOs().size()-1);
		String description = adjustmentDTO.getDescription();
		String subject = "Asset changed: " + asset.getName();
		String msg = "Software Reuse Repository message:\n\n" +
         				"An asset which you have consumed has been changed.\n\n" +
         				"Change description:\n" +
         				description + "\n\n" +
         				"Asset:\n" +
         				"ID: " + asset.getId() + "\n" +
         				"Name: " + asset.getName() + "\n" + 
         				"Version: " + asset.getVersion() + "\n" +
         				"State: " + asset.getState() + "\n\n" +
         				"Thanks.";
		List<String> toList = new ArrayList<String>();
		for (ConsumptionDTO consumptionDTO : asset.getUsage().getConsumptionDTOs()) {
			toList.add( consumptionDTO.getConsumerUserDTO().getEmail() );
		}
		if (toList.size() > 0) {
			EmailUtil.sendMail(toList, subject, msg);
		}
	}
	
	public static void sendNotificationToAuthorCertificationOk(Asset asset) {
		String to = asset.getUsage().getAuthorUserDTO().getEmail();
		String subject = "Asset certified: " + asset.getName();
		String msg = "Software Reuse Repository message:\n\n" +
         				asset.getUsage().getAuthorUserDTO().getName() + ",\n\n" +
         				"The asset was certified.\n\n" +
         				"Asset:\n" +
         				"ID: " + asset.getId() + "\n" +
         				"Name: " + asset.getName() + "\n" + 
         				"Version: " + asset.getVersion() + "\n" +
         				"State: " + asset.getState() + "\n\n" +
         				"Thanks.";
		EmailUtil.sendMail(to, subject, msg);
	}
	
	public static void sendNotificationToAuthorCertificationRejected(Asset asset, String messageForAuthor, String certifierName) {
		String to = asset.getUsage().getAuthorUserDTO().getEmail();
		String subject = "Asset not certified: " + asset.getName();
		String msg = "Software Reuse Repository message:\n\n" +
         				asset.getUsage().getAuthorUserDTO().getName() + ",\n\n" +
         				"The asset has not been certified, please perform the adjustments and send again to certification.\n\n" +
         				"Message from Certifier (" + certifierName + "):\n" +
         				messageForAuthor + "\n\n" +
         				"Asset:\n" +
							"ID: " + asset.getId() + "\n" +
							"Name: " + asset.getName() + "\n" + 
							"Version: " + asset.getVersion() + "\n" +
							"State: " + asset.getState() + "\n\n" +
							"Thanks.";
		EmailUtil.sendMail(to, subject, msg);
	}
	
	public static void sendNotificationToAuthorAssetRemoved(Asset asset, String messageForAuthor, String certifierName) {
		String to = asset.getUsage().getAuthorUserDTO().getEmail();
		String subject = "Asset removed: " + asset.getName();
		String msg = "Software Reuse Repository message:\n\n" +
         				asset.getUsage().getAuthorUserDTO().getName() + ",\n\n" +
         				"The asset has been removed.\n\n" +
         				"Message from Certifier (" + certifierName + "):\n" +
         				messageForAuthor + "\n\n" +
         				"Asset:\n" +
							"ID: " + asset.getId() + "\n" +
							"Name: " + asset.getName() + "\n" + 
							"Version: " + asset.getVersion() + "\n" +
							"State: " + asset.getState() + "\n\n" +
							"Thanks.";
		EmailUtil.sendMail(to, subject, msg);
	}
	
	public static void sendCommentNotificationToAuthors(Asset asset, UserCommentDTO userCommentDTO) {
		List<String> toAuthorsList = new ArrayList<String>();
		Collection<String> authorsUsernames = FieldsUtil.extractUsernames(asset.getUsage().getCreatorUsername());
		toAuthorsList = findEmailsFromUsernames(authorsUsernames);
		toAuthorsList.add(asset.getUsage().getAuthorUserDTO().getEmail());
		
		String subject = "New user comment on asset: " + asset.getName();
		String msg = "Software Reuse Repository message:\n\n" +
         				"Asset Author,\n\n" +
         				"There is a new comment from user " + userCommentDTO.getUserDTO().toString() + ":\n" +
         				userCommentDTO.getComment() + "\n\n" +
         				"Asset:\n" +
         				"ID: " + asset.getId() + "\n" +
         				"Name: " + asset.getName() + "\n" + 
         				"Version: " + asset.getVersion() + "\n" +
         				"State: " + asset.getState() + "\n\n" +
         				"Thanks.";
		EmailUtil.sendMail(toAuthorsList, subject, msg);
	}

	private static List<String> findEmailsFromUsernames(Collection<String> authorsUsernames) {
		List<String> emailList = new ArrayList<String>();
		for (String authorUsername : authorsUsernames) {
			UserDTO userWithUsernameDTO = UserDAO.getInstance().findByUsername(authorUsername);
			if (userWithUsernameDTO != null) {
				emailList.add(userWithUsernameDTO.getEmail());
			}
			else if (isLDAPUserAuthentication()) {
				//takes the emails from ldap
				emailList.addAll(new LDAPUserAuthentication().getEmailsFromLDAP(authorUsername));
			}
		}
		return emailList;
	}
	
	private static Boolean isLDAPUserAuthentication() {
		String userAuthenticationMode = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.USER_AUTHENTICATION_MODE);
		return userAuthenticationMode.equalsIgnoreCase(UserAuthentication.LDAP_MODE);
	}

}
