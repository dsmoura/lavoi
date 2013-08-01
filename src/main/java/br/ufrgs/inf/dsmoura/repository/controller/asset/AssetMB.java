package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;


import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.search.SearchMB;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrField;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrServerUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.XMLUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.ZipUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.AssetDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.GenericDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.AdjustmentDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetIDSequenceDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetSolrDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ConsumptionDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FeedbackDTO;


@KeepAlive
public class AssetMB implements Serializable, Validation {
	public static final String REFERENCE_MODEL = "@Library";
	public static final String REFERENCE_ARTIFACT = "Reference Artifact";

	private static final long serialVersionUID = 1L;

	private Asset asset;
	
	private Boolean isForGenerateID;
	private Boolean removeAssetFromRepository;
	
	private List<AssetSolrDTO> anotherVersionsList;
	
	private WizardMB wizardMB;
	private ClassificationMB classificationMB;
	private EffortMB effortMB;
	private RequirementsMB requirementsMB;
	private AnalysisMB analysisMB;
	private DesignMB designMB;
	private ImplementationMB implementationMB;
	private TestMB testMB;
	private UsageMB usageMB;
	private RelatedAssetsMB relatedAssetsMB;
	
	public final String PROJECTS_ONLY_ACCESS_RIGHTS = "RCWDRC--RC--R---R---";
	public final String ORG_PROJ_ONLY_ACCESS_RIGHTS = "RCWDRC--RC--RC--R---";
	public final String ALL_ORG_PROJ_ACCESS_RIGHTS = "RCWDRC--RC--RC--RC--";
	
	public final String DEFAULT_ACCESS_RIGHTS = ALL_ORG_PROJ_ACCESS_RIGHTS;
	
	private String adjustmentDescriptionAux;
	private String rejectionMessageAux;
	
	final Log logger = LogFactory.getLog(getClass());
	
	public AssetMB() {
		this.newAsset();
	}
	
	@PostConstruct
	public void init() {
		this.searchWithParameters();
	}
	
	private void searchWithParameters() {
		/* used for permalinks */
		String id = JSFUtil.getRequestParameter("id");
		String version = JSFUtil.getRequestParameter("version");
		if (StringUtils.isNotBlank(id) &&
				StringUtils.isNotBlank(version)) {
			/* it's ok, both parameters were passed */
			this.openAssetByIDAndVersion(id, version);
		}
		else if (StringUtils.isNotBlank(id) ||
					StringUtils.isNotBlank(version)) {
			/* it's an error, only one parameter was passed */
			this.asset = null;
		}
	}
	
	private void openAssetByIDAndVersion(String id, String version) {
		this.asset = AssetDAO.getInstance().findAssetByIDVersion(id, version);
		if (this.asset != null) {
			this.reinitMBs();
			this.loadAnotherVersions();
		}
	}
	
	public Boolean getIsValidAsset() {
		return this.asset != null;
	}
	
	public String newAsset() {
		this.asset = new Asset();
		this.asset.setIsExcluded(false);
		this.asset.setState(TypesDAO.getInstance().getInDefinitionAssetStateType());
		
		if (this.getIsForUseSoftwareLicense()) {
			this.loadGPLv3SoftwareLicense();
		}
		
		this.setDateToday();
		this.getDefaultAccessRigths();
		this.isForGenerateID = true;
		this.reinitMBs();
		return NavigationMB.NEW_ASSET;
	}
	
	public String editAsset() {
		String pkParam = JSFUtil.getRequestParameter("assetPkToEdit");
		this.asset = AssetDAO.getInstance().findUniqueByPk(Integer.valueOf(pkParam));
		this.adjustmentDescriptionAux = "";
		this.reinitMBs();
		this.loadAnotherVersions();
		this.isForGenerateID = true;
		this.relatedAssetsMB.loadSuggestedAssets();
		return NavigationMB.EDIT_ASSET;
	}
	
	public String openAsset() {
		String pkParam = JSFUtil.getRequestParameter("assetPkToOpen");
		return openAsset(Integer.valueOf(pkParam));
	}
	
	public String openAsset(Integer assetPk) {
		this.asset = AssetDAO.getInstance().findUniqueByPk(assetPk);
		this.reinitMBs();
		this.loadAnotherVersions();
		return NavigationMB.OPEN_ASSET;
	}
	
	public String saveInReadyForReuseState() {
		/* Ready for Reuse */
		this.asset.setState(TypesDAO.getInstance().getReadyForReuseAssetStateType());
		return this.save();
	}
	
	public String save() {
		/* Validations */
		Integer assetPk = AssetDAO.getInstance().findAssetPk(this.asset.getId(), this.asset.getVersion());
		if (assetPk != null) {
			if (this.asset.getAssetPk() == null) {
				JSFUtil.addErrorMessage("message_field", "Asset already published with same ID and version.");
				return "";
			}
			else if (this.asset.getAssetPk().compareTo(assetPk) != 0 ) {
				JSFUtil.addErrorMessage("message_field", "Asset already published with the same ID and version.");
				return "";
			}
		}
		/* Persist */
		if (this.asset.getAssetPk() == null) {
			/* Insert Asset */
			this.prepareAssetToInsert();
			this.asset = AssetDAO.getInstance().insert(this.asset);
			NotificationMB.sendNotificationsToRelatedAssets(this.asset);
		}
		else {
			/* Update Asset */
			if (this.getIsAuthor()) {
				if (this.adjustmentDescriptionAux == null || 
						this.adjustmentDescriptionAux.trim().length() == 0) {
					JSFUtil.addErrorMessage("assetAdjustmentDescriptionID", "Enter the Asset Adjustment Description.");
					return "";
				}
				this.addAdjustmentInAsset();
				this.asset = AssetDAO.getInstance().update(this.asset);
				NotificationMB.sendNotificationsAboutAdjustment(this.asset);
			}
			else if(this.getIsManagerUser()) {
				/* Manager adjustment */
				this.asset = AssetDAO.getInstance().update(this.asset);
			}
			else {
				throw new RuntimeException("The user " + JSFUtil.getLoggedUserDTO() + " cannot edit the asset (PK " + this.asset.getAssetPk() + ").");
			}
		}
		this.reinitMBs();
		return NavigationMB.PUBLISHED;
	}
	
	@Override
	public String validate() {
		if ( ! isForGenerateID ) {
			Integer assetPk = AssetDAO.getInstance().findAssetPk(this.asset.getId(), this.asset.getVersion());
			if (assetPk != null) {
				if (this.asset.getAssetPk() == null) {
					return "Asset already published with same ID and version.";
				}
				else if (this.asset.getAssetPk().compareTo(assetPk) != 0 ) {
					return "Asset already published with the same ID and version.";
				}
			}
		}
		return "";
	}
	
	private void reinitMBs() {
		this.classificationMB = new ClassificationMB(this);
		this.effortMB = new EffortMB(this);
		this.analysisMB = new AnalysisMB(this);
		this.designMB = new DesignMB(this);
		this.requirementsMB = new RequirementsMB(this);
		this.implementationMB = new ImplementationMB(this);
		this.testMB = new TestMB(this);
		this.usageMB = new UsageMB(this);
		this.relatedAssetsMB = new RelatedAssetsMB(this);
		this.wizardMB = new WizardMB(this.getAsset().getAssetPk() != null);
	}
	
	private void prepareAssetToInsert() {
		if (this.isForGenerateID) {
			AssetIDSequenceDTO assetIDSequenceDTO = new AssetIDSequenceDTO();
			assetIDSequenceDTO = (AssetIDSequenceDTO) GenericDAO.getInstance().insert(assetIDSequenceDTO);
			this.asset.setId(assetIDSequenceDTO.getAssetidsequencePk() + "");
		}
		/* Refresh the date of the insertion */
		this.setDateToday();
		/* Reuse Counter */
		this.asset.getClassification().setReuseCounter( 0 );
		/* Authorship */
		this.asset.getUsage().setAuthorshipDate(Calendar.getInstance());
		if (this.asset.getUsage().getAuthorUserDTO() == null) {
			this.asset.getUsage().setAuthorUserDTO(JSFUtil.getLoggedUserDTO());
		}
	}
	
	private void addAdjustmentInAsset() {
		/* Adjustments */
		AdjustmentDTO adjustmentDTO = new AdjustmentDTO();
		adjustmentDTO.setDate(Calendar.getInstance());
		adjustmentDTO.setAdjusterUserDTO(JSFUtil.getLoggedUserDTO());
		adjustmentDTO.setDescription(this.adjustmentDescriptionAux);
		this.asset.getUsage().getAdjustmentDTOs().add(adjustmentDTO);
	}
	
	public String downloadAsset() {
		if (!getIsAuthor()
				&& !getIsManagerUser()
				&& !getIsCertifierUser()) {
			this.consumeAsset();
		}
		if (!this.getCanDownloadAsset()) {
			throw new RuntimeException("User has no permission to download this asset.");
		}

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		try {
			/* Generate zip file */
			InputStream rassetXMLInputStream = XMLUtil.fromAssetToRassetXML(asset);
			ZipUtil.fromArtifactsToZipFile(response.getOutputStream(), rassetXMLInputStream, this.getAssetArtifacts());
			/* Download zip file */
			response.setHeader("Content-disposition", "attachment;filename=\"Asset - " + asset.getName() + ".zip\"");
			response.setContentType("application/zip");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return "";
	}
	
	private void consumeAsset() {
		if (!getIsAuthor()
				&& !getIsManagerUser()
				&& !getIsCertifierUser()) {
			this.addConsumptionAndFeedback();
		}
	}
	
	public Boolean getIsAuthor() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		String loggedUsername = JSFUtil.getLoggedUserDTO().getUsername();
		String authorUsername = this.asset.getUsage().getAuthorUserDTO().getUsername();
		if (authorUsername.equalsIgnoreCase(loggedUsername)) {
			return true;
		}
		Collection<String> authorsUsernames = FieldsUtil.extractUsernames(this.asset.getUsage().getCreatorUsername());
		if (authorsUsernames.contains(loggedUsername)) {
			return true;
		}
		return false;
	}
	
	public String assetCertification() {
		this.rejectionMessageAux = "";
		this.removeAssetFromRepository = false;
		return NavigationMB.CERTIFY_ASSET;
	}
	
	public String certifyAsset() {
		/* change state for Certified */
		this.asset.setState( TypesDAO.getInstance().getCertifiedAssetStateType() );
		/* add feedback */
		boolean hasNewFeedback = !this.getHasFeedback();
		if (hasNewFeedback) {
			FeedbackDTO feedbackDTO = new FeedbackDTO();
			feedbackDTO.setHasFeedback(false);
			feedbackDTO.setIsForCertification(true);
			feedbackDTO.setFeedbackUserDTO(JSFUtil.getLoggedUserDTO());
			feedbackDTO.setAsset(this.asset);
			this.asset.getFeedbackDTOs().add(feedbackDTO);
		}
		this.asset.getUsage().setCertificationDate(Calendar.getInstance());
		this.asset.getUsage().setCertifierUserDTO(JSFUtil.getLoggedUserDTO());
		/* update the asset */
		this.asset = AssetDAO.getInstance().update(this.asset);
		NotificationMB.sendNotificationToAuthorCertificationOk(this.asset);
		return "";
	}
	
	public String rejectCertification() {
		if (this.rejectionMessageAux == null
				|| this.rejectionMessageAux.trim().length() == 0) {
			JSFUtil.addErrorMessage("messageRejectionID", "Enter the message for the asset's author.");
			return "";
		}
		
		if (removeAssetFromRepository) {
			/* Notify the author */
			NotificationMB.sendNotificationToAuthorAssetRemoved(this.asset, this.rejectionMessageAux, JSFUtil.getLoggedUserDTO().getName());
			/* Mark as removed in database */
			this.asset.setIsExcluded(true);
			this.asset.setId( "_" + this.getAsset().getId() );
			this.asset.setVersion( "_" + this.getAsset().getVersion() );
			AssetDAO.getInstance().update(this.asset);
			/* Remove in index */
			SolrServerUtil.getInstance().deleteByQuery( SolrField.ASSET_PK.getName() + ":" + this.asset.getAssetPk());
			/* Clean the asset */
			this.newAsset();
			return NavigationMB.ASSET_REMOVED;
		}
		else {
			/* change state for In Review */
			this.asset.setState( TypesDAO.getInstance().getInReviewAssetStateType() );
			/* update the asset */
			this.asset = AssetDAO.getInstance().update(this.asset);
			NotificationMB.sendNotificationToAuthorCertificationRejected(this.asset, this.rejectionMessageAux, JSFUtil.getLoggedUserDTO().getName());
			return "";
		}
	}
	
	public String discontinueAsset() {
		/* change state for Discontinued */
		this.asset.setState( TypesDAO.getInstance().getDiscontinuedAssetStateType() );
		/* update the asset */
		this.asset = AssetDAO.getInstance().update(this.asset);
		return "";
	}
	
	public boolean isGPLSoftwareLicenseCompatible() {
		return this.asset.getSoftwareLicenseDTO() != null &&
					this.asset.getSoftwareLicenseDTO().getName() != null &&
					! this.asset.getSoftwareLicenseDTO().getName().equalsIgnoreCase("Other") &&
					this.asset.getSoftwareLicenseDTO().getIsGPLCompatible() != null &&
					this.asset.getSoftwareLicenseDTO().getIsGPLCompatible();
	}
	
	public boolean isGPLSoftwareLicenseNotCompatible() {
		return this.asset.getSoftwareLicenseDTO() != null &&
				this.asset.getSoftwareLicenseDTO().getName() != null &&
				! this.asset.getSoftwareLicenseDTO().getName().equalsIgnoreCase("Other") &&
				this.asset.getSoftwareLicenseDTO().getIsGPLCompatible() != null &&
				! this.asset.getSoftwareLicenseDTO().getIsGPLCompatible();
	}
	
	private void loadGPLv3SoftwareLicense() {
		asset.setSoftwareLicenseDTO( TypesDAO.getInstance().getGNUv3SoftwareLicenseDTO() );
	}
	
	public List<Artifactable> getAssetArtifacts() {
		List<Artifactable> artifacts = new ArrayList<Artifactable>();
		artifacts.addAll( this.getRequirementsMB().getArtifacts() );
		artifacts.addAll( this.getAnalysisMB().getArtifacts() );
		artifacts.addAll( this.getDesignMB().getArtifacts() );
		artifacts.addAll( this.getImplementationMB().getArtifacts() );
		artifacts.addAll( this.getTestMB().getArtifacts() );
		artifacts.addAll( this.getUsageMB().getArtifacts() );
		Collections.sort(artifacts);
		return artifacts;
	}
	
	public List<SelectItem> getArtifactsIDList() {
		List<SelectItem> artifactIDList = new ArrayList<SelectItem>();
		for (Artifactable artifactable : getAssetArtifacts()) {
			String label = artifactable.getId() + " - ";
			if (artifactable.getName() == null || artifactable.getName().length() == 0 ) {
				label += "(no name)";
			} else {
				label += FieldsUtil.normalize( artifactable.getName() );
			}
			artifactIDList.add( new SelectItem(artifactable.getId(), label) );
		}
		artifactIDList.add(0, new SelectItem(null, "Select..."));
		return artifactIDList;
	}
	
	public Long getNextArtifactID() {
		Long maxID = 0l;
		for (Artifactable artifactable : this.getAssetArtifacts()) {
			Long lID = Long.parseLong(artifactable.getId());
			if (lID > maxID) {
				maxID = lID;
			}
		}
		return maxID + 1l;
	}
	
	private void addConsumptionAndFeedback() {
		/* Consumption */
		boolean hasNewConsumption = !this.getHasConsumption();
		if (hasNewConsumption) {
			ConsumptionDTO consumptionDTO = new ConsumptionDTO();
			consumptionDTO.setConsumerUserDTO(JSFUtil.getLoggedUserDTO());
			consumptionDTO.setDate(Calendar.getInstance());
			this.asset.getUsage().getConsumptionDTOs().add(consumptionDTO);
			this.asset.getClassification().setReuseCounter( this.asset.getUsage().getConsumptionDTOs().size() );
			logger.info("Reuse counter = " + this.asset.getClassification().getReuseCounter());
		}
		
		boolean hasNewFeedback = !this.getHasFeedback();
		if (hasNewFeedback) {
			FeedbackDTO feedbackDTO = new FeedbackDTO();
			feedbackDTO.setHasFeedback(false);
			feedbackDTO.setIsForCertification(false);
			feedbackDTO.setFeedbackUserDTO(JSFUtil.getLoggedUserDTO());
			feedbackDTO.setAsset(this.asset);
			this.asset.getFeedbackDTOs().add(feedbackDTO);
		}
		
		if (hasNewConsumption || hasNewFeedback) {
			this.asset = AssetDAO.getInstance().update(this.asset);
		}
		else if ((!hasNewConsumption && hasNewFeedback) ||
						(hasNewConsumption && !hasNewFeedback)) {
			throw new RuntimeException("hasConsumption = " + hasNewConsumption + " and hasFeedback = " + hasNewFeedback);
		}
	}
	
	public String getRandomID() {
		int id = new Random().nextInt(100000000);
		this.asset.setId(id+"");
		this.loadAnotherVersions();
		return "";
	}
	
	public void loadAnotherVersions() {
		if (this.getHasID()) {
   		SearchMB searchMB = new SearchMB();
   		anotherVersionsList = searchMB.searchAnotherVersions(this.asset);
		}
		else {
			anotherVersionsList = new ArrayList<AssetSolrDTO>();
		}
	}
	
	public List<AssetSolrDTO> getAnotherVersions() {
		if (anotherVersionsList == null) {
			loadAnotherVersions();
		}
		return anotherVersionsList;
	}
	
	public Boolean getHasAnotherVersions() {
		return getAnotherVersions().size() > 0;
	}
	
	public Boolean getHasID() {
		return this.asset.getId() != null &&
					this.asset.getId().trim().length() > 0;
	}
	
	public void setDateToday() {
		this.asset.setDate(Calendar.getInstance());
	}
	
	public String getDefaultAccessRigths() {
		this.asset.setAccessRights(DEFAULT_ACCESS_RIGHTS);
		return "";
	}
	
	public Boolean getIsConsumer() {
		return this.getHasConsumption();
	}
	
	public Boolean getIsCertifierUser() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return JSFUtil.getLoggedUserDTO().getIsCertifier();
	}
	
	public Boolean getIsManagerUser() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return JSFUtil.getLoggedUserDTO().getIsManager();
	}
	
	public Boolean getCanSave() {
		return this.getAssetArtifacts().size() > 0;
	}
	
	public Boolean getCanEditAsset() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return (getIsAuthor() || getIsManagerUser())
				&& (!getIsCertifiedAsset())
				&& (!getIsDiscontinuedAsset());
	}
	
	public Boolean getCanCertifyAsset() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return ( getIsReadyForReuseAsset() )
				&& ( JSFUtil.getLoggedUserDTO().getIsCertifier() );
	}
	
	public Boolean getCanDiscontinueAsset() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return ( ! getIsDiscontinuedAsset() )
				&& ( JSFUtil.getLoggedUserDTO().getIsManager() );
	}
	
	public Boolean getCanConsumeAsset() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return (!this.getIsConsumer()) &&
				(!this.getIsAuthor()) &&
				(!this.getIsCertifierUser() &&
				(!this.getIsManagerUser()));
	}
	
	public Boolean getCanDownloadAsset() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return this.getIsConsumer() ||
				this.getIsAuthor() ||
				this.getIsCertifierUser() ||
				this.getIsManagerUser();
	}
	
	public Boolean getIsCertifiedAsset() {
		return this.asset != null && 
					this.asset.getState() != null &&
					this.asset.getState().getName() != null &&
					this.asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getCertifiedAssetStateType().getName() );
	}
	
	public Boolean getIsInDefinitionAsset() {
		return this.asset != null && 
					this.asset.getState() != null &&
					this.asset.getState().getName() != null &&
					this.asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getInDefinitionAssetStateType().getName() );
	}
	
	public Boolean getIsInReviewAsset() {
		return this.asset != null && 
					this.asset.getState() != null &&
					this.asset.getState().getName() != null &&
					this.asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getInReviewAssetStateType().getName() );
	}
	
	public Boolean getIsReadyForReuseAsset() {
		return this.asset != null && 
					this.asset.getState() != null &&
					this.asset.getState().getName() != null &&
					this.asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getReadyForReuseAssetStateType().getName() );
	}
	
	public Boolean getIsDiscontinuedAsset() {
		return this.asset != null && 
					this.asset.getState() != null &&
					this.asset.getState().getName() != null &&
					this.asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getDiscontinuedAssetStateType().getName() );
	}
	
	public String getPublishMessage() {
		if (getIsReadyForReuseAsset()) {
			return "This asset will be sent for certification.";
		}
		return "";
	}
	
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	public Boolean getIsForGenerateID() {
		return isForGenerateID;
	}
	
	public Boolean getIsOtherType() {
		return FieldsUtil.isValidType(this.asset.getType()) &&
					this.asset.getType().getName().equalsIgnoreCase("Other");
	}
	
	public Boolean getIsForUseSoftwareLicense() {
		return TypesDAO.getInstance().getSoftwareLicenseDTOList().size() > 0;
	}
	
	public Boolean getIsOtherSoftwareLicense() {
		return FieldsUtil.isValidSoftwareLicense(this.asset.getSoftwareLicenseDTO()) &&
					this.asset.getSoftwareLicenseDTO().getName().equalsIgnoreCase("Other");
	}
	
	public void setIsForGenerateID(Boolean isForGenerateID) {
		this.isForGenerateID = isForGenerateID;
	}

	public Date getDateTime() {
		if (asset.getDate() != null) {
			return asset.getDate().getTime();
		}
		return null;
	}
	
	public void setDateTime(Date dateTime) {
		if (dateTime != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dateTime);
			this.asset.setDate(c);
		}
		else {
			this.asset.setDate(null);
		}
	}
	
	public Date getCreationDateTime() {
		if (asset.getCreationDate() != null) {
			return asset.getCreationDate().getTime();
		}
		return null;
	}
	
	public void setCreationDateTime(Date creationDateTime) {
		if (creationDateTime != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(creationDateTime);
			this.asset.setCreationDate(c);
		}
		else {
			this.asset.setCreationDate(null);
		}
	}
	
	public Boolean getRemoveAssetFromRepository() {
		return removeAssetFromRepository;
	}

	public void setRemoveAssetFromRepository(Boolean removeAssetFromRepository) {
		this.removeAssetFromRepository = removeAssetFromRepository;
	}
	
	public Boolean getCanEvaluate() {
		if (!JSFUtil.isLoggedUser()) {
			return false;
		}
		return this.getHasFeedback();
	}

	public Boolean getHasFeedback() {
		for (FeedbackDTO f : this.asset.getFeedbackDTOs()) {
			if (f.getFeedbackUserDTO().getUsername().equalsIgnoreCase( JSFUtil.getLoggedUserDTO().getUsername() )) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean getHasConsumption() {
		for (ConsumptionDTO c : this.asset.getUsage().getConsumptionDTOs()) {
			if (c.getConsumerUserDTO().getUsername().equalsIgnoreCase( JSFUtil.getLoggedUserDTO().getUsername()) ) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean getShowReference() {
		return getCanDownloadAsset() ||
				(JSFUtil.isLoggedUser() && this.getHasConsumption()) ;
	}
	
	public String getPermalinkURL() {
		return JSFUtil.getRequestBaseURL() +
				"/openAsset.jsf?id=" + this.asset.getId() +
				"&version=" + this.asset.getVersion();
	}
	
	public String getProjectsOnlyAccessRigths() {
		return PROJECTS_ONLY_ACCESS_RIGHTS;
	}
	
	public String getOrgProjOnlyAccessRigths() {
		return ORG_PROJ_ONLY_ACCESS_RIGHTS;
	}
	
	public String getAllAccessRigths() {
		return ALL_ORG_PROJ_ACCESS_RIGHTS;
	}
	
	public WizardMB getWizardMB() {
		return wizardMB;
	}
	public void setWizardMB(WizardMB wizardMB) {
		this.wizardMB = wizardMB;
	}
	public ClassificationMB getClassificationMB() {
		return classificationMB;
	}
	public void setClassificationMB(ClassificationMB classificationMB) {
		this.classificationMB = classificationMB;
	}
	public EffortMB getEffortMB() {
		return effortMB;
	}
	public void setEffortMB(EffortMB effortMB) {
		this.effortMB = effortMB;
	}
	public RequirementsMB getRequirementsMB() {
		return requirementsMB;
	}
	public void setRequirementsMB(RequirementsMB requirementsMB) {
		this.requirementsMB = requirementsMB;
	}
	public AnalysisMB getAnalysisMB() {
		return analysisMB;
	}
	public void setAnalysisMB(AnalysisMB analysisMB) {
		this.analysisMB = analysisMB;
	}
	public DesignMB getDesignMB() {
		return designMB;
	}
	public void setDesignMB(DesignMB designMB) {
		this.designMB = designMB;
	}
	public ImplementationMB getImplementationMB() {
		return implementationMB;
	}
	public void setImplementationMB(ImplementationMB implementationMB) {
		this.implementationMB = implementationMB;
	}
	public TestMB getTestMB() {
		return testMB;
	}
	public void setTestMB(TestMB testMB) {
		this.testMB = testMB;
	}
	public UsageMB getUsageMB() {
		return usageMB;
	}
	public void setUsageMB(UsageMB usageMB) {
		this.usageMB = usageMB;
	}
	public RelatedAssetsMB getRelatedAssetsMB() {
		return relatedAssetsMB;
	}
	public void setRelatedAssetsMB(RelatedAssetsMB relatedAssetsMB) {
		this.relatedAssetsMB = relatedAssetsMB;
	}
	public String getAdjustmentDescriptionAux() {
		return adjustmentDescriptionAux;
	}
	public void setAdjustmentDescriptionAux(String adjustmentDescriptionAux) {
		this.adjustmentDescriptionAux = adjustmentDescriptionAux;
	}
	public String getRejectionMessageAux() {
		return rejectionMessageAux;
	}
	public void setRejectionMessageAux(String rejectionMessageAux) {
		this.rejectionMessageAux = rejectionMessageAux;
	}
	
}
