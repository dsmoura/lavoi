package br.ufrgs.inf.dsmoura.repository.controller.solr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.AdjustmentDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetSolrDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ConsumptionDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.DescriptorGroupDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FreeFormDescriptorDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InterfaceSpecDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InternationalizationTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationalSystemTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProjectDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAsset;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TagDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UseCaseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserCommentDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.WsdlDTO;

public class SolrConversionUtil {
	
	final static Log logger = LogFactory.getLog(SolrConversionUtil.class);
	
	public static SolrInputDocument fromAssetToSolrInputDocument(Asset asset) {
		/* ASSET */
		SolrInputDocument doc = new SolrInputDocument();
		
		addNotNull(doc, SolrField.ASSET_PK.getName(), asset.getAssetPk().toString());
		addNotNull(doc, SolrField.ID.getName(), asset.getId());
		addNotNull(doc, SolrField.NAME.getName(), asset.getName());
		if (asset.getDate() != null) {
			addNotNull(doc, SolrField.DATE.getName(), asset.getDate().getTime());
		}
		if (asset.getCreationDate() != null) {
			addNotNull(doc, SolrField.CREATION_DATE.getName(), asset.getCreationDate().getTime());
		}
		if (asset.getType() != null) {
			addNotNull(doc, SolrField.TYPE.getName(), asset.getType().getName());
			if (FieldsUtil.isValidType(asset.getType()) &&
					asset.getType().getName().equalsIgnoreCase("Other")) {
				addNotNull(doc, SolrField.OTHER_TYPE.getName(), asset.getOtherType());
			}
		}
		if (asset.getState() != null) {
			addNotNull(doc, SolrField.STATE.getName(), asset.getState().getName());
		}
		if (asset.getSoftwareLicenseDTO() != null) {
			addNotNull(doc, SolrField.SOFTWARE_LICENSE_ASSET.getName(), asset.getSoftwareLicenseDTO().getName());
			if (FieldsUtil.isValidSoftwareLicense(asset.getSoftwareLicenseDTO()) &&
					asset.getSoftwareLicenseDTO().getName().equalsIgnoreCase("Other")) {
				addNotNull(doc, SolrField.OTHER_SOFTWARE_LICENSE_ASSET.getName(), asset.getOtherSoftwareLicense());
			}
		}
		addNotNull(doc, SolrField.VERSION.getName(), asset.getVersion());
		addNotNull(doc, SolrField.SHORT_DESCRIPTION.getName(), asset.getShortDescription());
		addNotNull(doc, SolrField.DESCRIPTION.getName(), asset.getDescription());
		
		/* USAGE */
		addNotNull(doc, SolrField.USAGE_DESCRIPTION.getName(), asset.getUsage().getDescription());
		
		addNotNull(doc, SolrField.AUTHOR_NAME.getName(), asset.getUsage().getAuthorUserDTO().getName());
		addNotNull(doc, SolrField.AUTHOR_USERNAME.getName(), asset.getUsage().getAuthorUserDTO().getUsername());
		
		Collection<String> authorsUsernames = FieldsUtil.extractUsernames(asset.getUsage().getCreatorUsername());
		for (String authorUsername : authorsUsernames) {
			addNotNull(doc, SolrField.AUTHOR_USERNAME.getName(), authorUsername);
		}
		
		addNotNull(doc, SolrField.CREATOR_NAME.getName(), asset.getUsage().getCreatorUsername());
		
		if (asset.getUsage().getCertifierUserDTO() != null) {
			addNotNull(doc, SolrField.CERTIFIER_USERNAME.getName(), asset.getUsage().getCertifierUserDTO().getUsername());
			addNotNull(doc, SolrField.CERTIFIER_NAME.getName(), asset.getUsage().getCertifierUserDTO().getName());
		}
		if (asset.getUsage().getCertificationDate() != null) {
			addNotNull(doc, SolrField.CERTIFICATION_DATE.getName(), asset.getUsage().getCertificationDate().getTime());
		}
		for (ConsumptionDTO consumptionDTO : asset.getUsage().getConsumptionDTOs()) {
			addNotNull(doc, SolrField.CONSUMER_USERNAME.getName(), consumptionDTO.getConsumerUserDTO().getUsername());
			addNotNull(doc, SolrField.CONSUMER_NAME.getName(), consumptionDTO.getConsumerUserDTO().getName());
		}
		for (AdjustmentDTO adjustmentDTO : asset.getUsage().getAdjustmentDTOs()) {
			addNotNull(doc, SolrField.ADJUSTMENT_DESCRIPTION.getName(), adjustmentDTO.getDescription());
		}
		for (UserCommentDTO userCommentDTO : asset.getUsage().getUserCommentDTOs()) {
			addNotNull(doc, SolrField.USER_COMMENT.getName(), userCommentDTO.getComment());
			addNotNull(doc, SolrField.USER_COMMENT_USERNAME.getName(), userCommentDTO.getUserDTO().getUsername());
			addNotNull(doc, SolrField.USER_COMMENT_USER_NAME.getName(), userCommentDTO.getUserDTO().getName());
		}
		
		/* CLASSIFICATION */
		if (asset.getClassification().getAverageScore() == null) {
			addNotNull(doc, SolrField.AVERAGE_SCORE.getName(), -1);
		} else {
			addNotNull(doc, SolrField.AVERAGE_SCORE.getName(), asset.getClassification().getAverageScore());
		}
		
		addNotNull(doc, SolrField.REUSE_COUNTER.getName(), asset.getClassification().getReuseCounter());
		
		for (ApplicationSubdomain asd : asset.getClassification().getApplicationSubdomains()) {
			addNotNull(doc, SolrField.APPLICATION_DOMAIN.getName(), asd.getApplicationDomain().getName());
			addNotNull(doc, SolrField.APPLICATION_SUBDOMAIN.getName(), asd.getName());
			if (FieldsUtil.isValidApplicationSubdomain(asd) &&
					asd.getName().equalsIgnoreCase("Other")) {
				addNotNull(doc, SolrField.OTHER_APPLICATION_DOMAIN.getName(), asset.getClassification().getOtherApplicationDomain());
				addNotNull(doc, SolrField.OTHER_APPLICATION_SUBDOMAIN.getName(), asset.getClassification().getOtherApplicationSubdomain());
			}
		}
		for (ProjectDTO projDTO : asset.getClassification().getProjectDTOs()) {
			addNotNull(doc, SolrField.ORGANIZATION.getName(), projDTO.getOrganizationDTO().getName());
			addNotNull(doc, SolrField.PROJECT.getName(), projDTO.getName());
		}
		for (DescriptorGroupDTO groupDTO : asset.getClassification().getDescriptorGroupDTOs()) {
			addNotNull(doc, SolrField.DESCRIPTOR_GROUP.getName(), groupDTO.getName());
			for (FreeFormDescriptorDTO descriptorDTO : groupDTO.getFreeFormDescriptorDTOs()) {
				addNotNull(doc, SolrField.DESCRIPTOR_NAME.getName(), descriptorDTO.getName());
				addNotNull(doc, SolrField.DESCRIPTOR_VALUE.getName(), descriptorDTO.getFreeFormValue());
			}
		}
		for (TagDTO tag : asset.getClassification().getTagDTOs()) {
			addNotNull(doc, SolrField.TAG.getName(), tag.getName());
		}
		
		/* REQUIREMENTS */
		for (FunctionalRequirementDTO functionalReq : asset.getSolution().getRequirements().getFunctionalRequirementDTOs()) {
			addArtifactable(doc, functionalReq);
		}
		for (UseCaseDTO useCaseDTO : asset.getSolution().getRequirements().getUseCaseDTOs()) {
			addArtifactable(doc, useCaseDTO);
		}
		for (UserInterfaceDTO userInterfaceDTO : asset.getSolution().getRequirements().getUserInterfaceDTOs()) {
			addArtifactable(doc, userInterfaceDTO);
		}
		for (NonFunctionalRequirementDTO nonFunctionalReq : asset.getSolution().getRequirements().getNonFunctionalRequirementDTOs()) {
			addArtifactable(doc, nonFunctionalReq);
		}
		for (InternationalizationTypeDTO language : asset.getSolution().getRequirements().getInternationalizationTypeDTOs()) {
			addNotNull(doc, SolrField.LANGUAGE.getName(), language.getName());
		}
		for (OperationalSystemTypeDTO operSyst : asset.getSolution().getRequirements().getOperationalSystemTypeDTOs()) {
			addNotNull(doc, SolrField.OPERATIONAL_SYSTEM.getName(), operSyst.getName());
		}
		
		/* ANALYSIS */
		for (InterfaceSpecDTO intSpecDTO : asset.getSolution().getAnalysis().getInterfaceSpecDTOs()) {
			addNotNull(doc, SolrField.INTERFACE_SPEC.getName(), intSpecDTO.getName());
			for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
				addNotNull(doc, SolrField.OPERATION_NAME.getName(), operationDTO.getName());
				addNotNull(doc, SolrField.OPERATION_DESCRIPTION.getName(), operationDTO.getDescription());
			}
		}
		for (UseCaseDTO useCaseDTO : asset.getSolution().getAnalysis().getUseCaseDTOs()) {
			addArtifactable(doc, useCaseDTO);
		}
		for (UserInterfaceDTO userInterfaceDTO : asset.getSolution().getAnalysis().getUserInterfaceDTOs()) {
			addArtifactable(doc, userInterfaceDTO);
		}
		for (ArtifactDTO artifactDTO : asset.getSolution().getAnalysis().getArtifactDTOs()) {
			addArtifactable(doc, artifactDTO);
		}
		
		/* DESIGN */
		for (InterfaceSpecDTO intSpecDTO : asset.getSolution().getDesign().getInterfaceSpecDTOs()) {
			addNotNull(doc, SolrField.INTERFACE_SPEC.getName(), intSpecDTO.getName());
			for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
				addNotNull(doc, SolrField.OPERATION_NAME.getName(), operationDTO.getName());
				addNotNull(doc, SolrField.OPERATION_DESCRIPTION.getName(), operationDTO.getDescription());
			}
		}
		for (DesignPatternDTO desPatDTO : asset.getSolution().getDesign().getDesignPatternDTOs()) {
			addNotNull(doc, SolrField.DESIGN_PATTERN.getName(), desPatDTO.getName());
		}
		for (Artifactable artifactDTO : asset.getSolution().getDesign().getArtifactDTOs()) {
			addArtifactable(doc, artifactDTO);
		}
		for (UserInterfaceDTO userInterfaceDTO : asset.getSolution().getDesign().getUserInterfaceDTOs()) {
			addArtifactable(doc, userInterfaceDTO);
		}
		
		/* IMPLEMENTATION */
		for (DesignPatternDTO desPatDTO : asset.getSolution().getImplementation().getDesignPatternDTOs()) {
			addNotNull(doc, SolrField.DESIGN_PATTERN.getName(), desPatDTO.getName());
		}
		for (ProgrammingLanguageDTO progLangDTO : asset.getSolution().getImplementation().getProgrammingLanguageDTOs()) {
			addNotNull(doc, SolrField.PROGRAMMING_LANGUAGE.getName(), progLangDTO.getName());
			if (FieldsUtil.isValidProgrammingLanguage(progLangDTO) &&
					progLangDTO.getName().equalsIgnoreCase("Other")) {
				addNotNull(doc, SolrField.OTHER_PROGRAMMING_LANGUAGE.getName(), asset.getSolution().getImplementation().getOtherProgrammingLanguage());				
			}
		}
		for (Artifactable artifactDTO : asset.getSolution().getImplementation().getArtifactDTOs()) {
			addArtifactable(doc, artifactDTO);
		}
		for (Artifactable wsdlDTO : asset.getSolution().getImplementation().getWsdlDTOs()) {
			addArtifactable(doc, wsdlDTO);
		}
		for (SourceCodeDTO sourceCodeDTO : asset.getSolution().getImplementation().getSourceCodeDTOs()) {
			addArtifactable(doc, sourceCodeDTO);
		}
		for (UserInterfaceDTO userInterfaceDTO : asset.getSolution().getImplementation().getUserInterfaceDTOs()) {
			addArtifactable(doc, userInterfaceDTO);
		}
		
		/* TEST */
		for (TestTypeDTO testTypeDTO : asset.getSolution().getTest().getTestTypeDTOs()) {
			addNotNull(doc, SolrField.TEST_TYPE.getName(), testTypeDTO.getName());
		}
		for (TestMethodTypeDTO testMethodTypeDTO : asset.getSolution().getTest().getTestMethodTypeDTOs()) {
			addNotNull(doc, SolrField.TEST_METHOD_TYPE.getName(), testMethodTypeDTO.getName());
		}
		for (DesignPatternDTO desPatDTO : asset.getSolution().getTest().getDesignPatternDTOs()) {
			addNotNull(doc, SolrField.DESIGN_PATTERN.getName(), desPatDTO.getName());
		}
		for (ProgrammingLanguageDTO progLangDTO : asset.getSolution().getTest().getProgrammingLanguageDTOs()) {
			addNotNull(doc, SolrField.PROGRAMMING_LANGUAGE.getName(), progLangDTO.getName());
			if (FieldsUtil.isValidProgrammingLanguage(progLangDTO) &&
					progLangDTO.getName().equalsIgnoreCase("Other")) {
				addNotNull(doc, SolrField.OTHER_PROGRAMMING_LANGUAGE.getName(), asset.getSolution().getTest().getOtherProgrammingLanguage());				
			}
		}
		for (Artifactable artifactDTO : asset.getSolution().getTest().getArtifactDTOs()) {
			addArtifactable(doc, artifactDTO);
		}
		for (SourceCodeDTO sourceCodeDTO : asset.getSolution().getTest().getSourceCodeDTOs()) {
			addArtifactable(doc, sourceCodeDTO);
		}
		
		/* USAGE */
		for (Artifactable artifactDTO : asset.getUsage().getArtifactDTOs()) {
			addArtifactable(doc, artifactDTO);
		}
		
		/* RELATED ASSETS */
		for (RelatedAsset relatedAsset : asset.getRelatedAssets()) {
			addNotNull(doc, SolrField.RELATED_ASSET_ID.getName(), relatedAsset.getId());
			addNotNull(doc, SolrField.RELATED_ASSET_NAME.getName(), relatedAsset.getName());
			addNotNull(doc, SolrField.RELATED_ASSET_VERSION.getName(), relatedAsset.getVersion());
			addNotNull(doc, SolrField.REFERENCE.getName(), relatedAsset.getReference());
			if (relatedAsset.getRelatedAssetTypeDTO() != null) {
				addNotNull(doc, SolrField.RELATED_ASSET_TYPE.getName(), relatedAsset.getRelatedAssetTypeDTO().getName());
			}
		}
		
		return doc;
	}
	
	private static void addFileNotNull(SolrInputDocument doc, Artifactable file) {
		if (file == null || file.getFile() == null) {
			return;
		}
		ContentHandler textHandler = new BodyContentHandler(10*1024*1024);
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();

		InputStream input = new ByteArrayInputStream(file.getFile());
		try {
			new AutoDetectParser().parse(input, textHandler, metadata, context);
		} catch (Exception e) {
			logger.error(("File parsing failed: " + file.getName()), e);
			return;
		}
		doc.addField(SolrField.ARTIFACT_TEXT.getName(), textHandler.toString());
		logger.info(SolrField.ARTIFACT_TEXT.getName() + " : " + textHandler.toString());
	}
	
	public static List<AssetSolrDTO> fromSolrDocumentListToAsset(SolrDocumentList solrAssetDocs) {
		return fromSolrDocumentListToAsset(solrAssetDocs, null);
	}
	
	public static List<AssetSolrDTO> fromSolrDocumentListToAsset(SolrDocumentList solrAssetDocs, Float maxScore) {
		List<AssetSolrDTO> assets = new ArrayList<AssetSolrDTO>();
		for (SolrDocument d : solrAssetDocs) {
			AssetSolrDTO assetSolrDTO = new AssetSolrDTO();

			assetSolrDTO.setScore( (Float) d.getFieldValue(SolrField.SCORE.getName()) );
			assetSolrDTO.setAverageScore( (Float) d.getFieldValue(SolrField.AVERAGE_SCORE.getName()) );
			assetSolrDTO.setReuseCounter( (Integer) d.getFieldValue(SolrField.REUSE_COUNTER.getName()) );
			
			assetSolrDTO.setAssetPk( (String) d.getFieldValue(SolrField.ASSET_PK.getName()) );
			
			assetSolrDTO.setId( (String) d.getFieldValue(SolrField.ID.getName()) );
			assetSolrDTO.setName( (String) d.getFieldValue(SolrField.NAME.getName()) );
			
			if (d.getFieldValue(SolrField.DATE.getName()) != null) {
				Calendar c = Calendar.getInstance();
				c.setTime((Date)d.getFieldValue(SolrField.DATE.getName()));
				assetSolrDTO.setDate(c);
			}
			
			assetSolrDTO.setVersion( (String) d.getFieldValue(SolrField.VERSION.getName()) );
			assetSolrDTO.setType( (String) d.getFieldValue(SolrField.TYPE.getName()) );
			if (assetSolrDTO.getType().equalsIgnoreCase("OTHER")) {
				assetSolrDTO.setType( assetSolrDTO.getType() + " (" + (String) d.getFieldValue(SolrField.OTHER_TYPE.getName()) + ")" );
			}
			assetSolrDTO.setState( (String) d.getFieldValue(SolrField.STATE.getName()) );
			assetSolrDTO.setSoftwareLicense( (String) d.getFieldValue(SolrField.SOFTWARE_LICENSE_ASSET.getName()) );
			
			assetSolrDTO.setShortDescription( (String) d.getFieldValue(SolrField.SHORT_DESCRIPTION.getName()) );
			assetSolrDTO.setDescription( (String) d.getFieldValue(SolrField.DESCRIPTION.getName()) );
			
			if (d.getFieldValue(SolrField.CERTIFICATION_DATE.getName()) != null) {
				Calendar c = Calendar.getInstance();
				c.setTime((Date)d.getFieldValue(SolrField.CERTIFICATION_DATE.getName()));
				assetSolrDTO.setCertificationDate(c);
			}
			
			assets.add(assetSolrDTO);
		}
		
		/* Normalize the relevance of the query */
		if (maxScore == null) {
			maxScore = solrAssetDocs.getMaxScore();
		}
		for (AssetSolrDTO a : assets) {
			float r = 1 + ( a.getScore() * 9f ) / maxScore;
			a.setStars((int)r);
		}
		
		return assets;
	}
	
	/** Returns a query with fields and texts. */
	public static String fromAssetToQuery(Asset asset) {
		List<Term> terms = fromAssetToTerms(asset);
		return fromTermsToQuery(terms);
	}
	
	/** Returns a query with just texts. */
	public static String fromAssetToTextQuery(Asset asset) {
		List<Term> terms = fromAssetToTerms(asset);
		return fromTermsToQueryText(terms);
	}
	
   public static String fromStringToQuery(String value) {
   	String query = "";
   	boolean isOpenedQuote = false;
   	Scanner sc = new Scanner(value);
   	while (sc.hasNext()) {
   		String next = sc.next();
			if (next.contains("\"") || isOpenedQuote) {
				query += next;
				if (next.contains("\"")) {
					isOpenedQuote = !isOpenedQuote;
				}
			}
			else {
				if (next.contains(":")) {
					query += next.replace(":", ":*") + "*";
				} else {
					query += "*" + next + "*";
				}
			}
			if (!isOpenedQuote) {
   			query += " OR ";
   		} else {
				query += " ";
			}
   	}
   	if (query.endsWith(" OR ")) {
   		return query.substring(0, query.lastIndexOf(" OR "));
		}
   	else {
   		return query;
   	}
   }
   
   public static List<Term> fromAssetToTerms(Asset asset) {
   	ArrayList<Term> terms = new ArrayList<Term>();
   	terms.addAll( extractTerms(SolrField.ID.getName(), asset.getId(), true) );
   	terms.addAll( extractTerms(SolrField.NAME.getName(), asset.getName(), false) );
   	terms.addAll( extractTerms(SolrField.VERSION.getName(), asset.getVersion(), false) );
   	if (asset.getType() != null) {
   		terms.addAll( extractTerms(SolrField.TYPE.getName(), asset.getType().getName(), true) );
   		if (FieldsUtil.isValidType(asset.getType()) &&
   				asset.getType().getName().equalsIgnoreCase("Other")) {
   			terms.addAll( extractTerms(SolrField.OTHER_TYPE.getName(), asset.getOtherType(), true) );   			
   		}
   	}
   	if (asset.getState() != null) {
   		terms.addAll( extractTerms(SolrField.STATE.getName(), asset.getState().getName(), true) );		
   	}
   	if (asset.getSoftwareLicenseDTO() != null) {
   		terms.addAll( extractTerms(SolrField.SOFTWARE_LICENSE_ASSET.getName(), asset.getSoftwareLicenseDTO().getName(), true) );
   		if (FieldsUtil.isValidSoftwareLicense(asset.getSoftwareLicenseDTO()) &&
					asset.getSoftwareLicenseDTO().getName().equalsIgnoreCase("Other")) {
   			terms.addAll( extractTerms(SolrField.OTHER_SOFTWARE_LICENSE_ASSET.getName(), asset.getOtherSoftwareLicense(), true) );
   		}
   	}
   	
   	/* USAGE */
   	if (asset.getUsage().getCreatorUsername() != null) {
   		terms.addAll( extractTerms(SolrField.CREATOR_NAME.getName(), asset.getUsage().getCreatorUsername(), true) );
   	}
   	if (asset.getUsage().getAuthorUserDTO() != null) {
   		terms.addAll( extractTerms(SolrField.AUTHOR_USERNAME.getName(), asset.getUsage().getAuthorUserDTO().getUsername(), true) );
      	terms.addAll( extractTerms(SolrField.AUTHOR_NAME.getName(), asset.getUsage().getAuthorUserDTO().getName(), true) );
   	}
   	if (asset.getUsage().getCertifierUserDTO() != null) {
   		terms.addAll( extractTerms(SolrField.CERTIFIER_USERNAME.getName(), asset.getUsage().getCertifierUserDTO().getUsername(), true) );
      	terms.addAll( extractTerms(SolrField.CERTIFIER_NAME.getName(), asset.getUsage().getCertifierUserDTO().getName(), true) );
   	}
   	for (ConsumptionDTO consumptionDTO : asset.getUsage().getConsumptionDTOs()) {
   		terms.addAll( extractTerms(SolrField.CONSUMER_USERNAME.getName(), consumptionDTO.getConsumerUserDTO().getUsername(), true) );
      	terms.addAll( extractTerms(SolrField.CONSUMER_NAME.getName(), consumptionDTO.getConsumerUserDTO().getName(), true) );
   	}
		for (UserCommentDTO userCommentDTO : asset.getUsage().getUserCommentDTOs()) {
			terms.addAll( extractTerms(SolrField.USER_COMMENT_USERNAME.getName(), userCommentDTO.getUserDTO().getUsername(), true) );
			terms.addAll( extractTerms(SolrField.USER_COMMENT_USER_NAME.getName(), userCommentDTO.getUserDTO().getName(), true) );
		}
   	
   	/* CLASSIFICATION */
   	for (ApplicationSubdomain applicationSubdomain : asset.getClassification().getApplicationSubdomains()) {
   		if (applicationSubdomain.getName() != null) {
     			terms.addAll( extractTerms(SolrField.APPLICATION_SUBDOMAIN.getName(), applicationSubdomain.getName(), true) );
     		}
     		if (applicationSubdomain.getApplicationDomain() != null &&
     				applicationSubdomain.getApplicationDomain().getName() != null) {
     			terms.addAll( extractTerms(SolrField.APPLICATION_DOMAIN.getName(), applicationSubdomain.getApplicationDomain().getName(), true) );
     		}
     		if (FieldsUtil.isValidApplicationSubdomain(applicationSubdomain) &&
     				applicationSubdomain.getName().equalsIgnoreCase("Other")) {
     			terms.addAll( extractTerms(SolrField.OTHER_APPLICATION_DOMAIN.getName(), asset.getClassification().getOtherApplicationDomain(), true) );
     			terms.addAll( extractTerms(SolrField.OTHER_APPLICATION_SUBDOMAIN.getName(), asset.getClassification().getOtherApplicationSubdomain(), true) );
     		}
   	}
   	for (ProjectDTO project : asset.getClassification().getProjectDTOs()) {
   		if (project.getName() != null) {
     			terms.addAll( extractTerms(SolrField.PROJECT.getName(), project.getName(), true) );
     		}
   		if (project.getOrganizationDTO().getName() != null) {
     			terms.addAll( extractTerms(SolrField.ORGANIZATION.getName(), project.getOrganizationDTO().getName(), true) );
     		}
   	}
   	for (TagDTO tagDTO : asset.getClassification().getTagDTOs()) {
   		if (tagDTO != null) {
   			terms.addAll( extractTerms(SolrField.TAG.getName(), tagDTO.getName(), true) );
   		}
   	}
   	
   	/* REQUIREMENTS */
   	for (InternationalizationTypeDTO internat : asset.getSolution().getRequirements().getInternationalizationTypeDTOs()) {
   		if (internat != null) {
   			terms.addAll( extractTerms(SolrField.LANGUAGE.getName(), internat.getName(), true) );
   		}
   	}
   	for (OperationalSystemTypeDTO operSyst : asset.getSolution().getRequirements().getOperationalSystemTypeDTOs()) {
   		if (operSyst != null) {
   			terms.addAll( extractTerms(SolrField.OPERATIONAL_SYSTEM.getName(), operSyst.getName(), true) );
   		}
   	}
   	for (FunctionalRequirementDTO functional : asset.getSolution().getRequirements().getFunctionalRequirementDTOs()) {
   		if (functional != null && functional.getFunctionalRequirementTypeDTO() != null) {
   			terms.addAll( extractTerms(SolrField.FUNCTIONAL_REQUIREMENT_TYPE.getName(), functional.getFunctionalRequirementTypeDTO().getName(), true) );
   		}
   	}
   	for (NonFunctionalRequirementDTO nonFunctional : asset.getSolution().getRequirements().getNonFunctionalRequirementDTOs()) {
   		if (nonFunctional != null && nonFunctional.getNonFunctionalRequirementTypeDTO() != null) {
   			terms.addAll( extractTerms(SolrField.NON_FUNCTIONAL_REQUIREMENT_TYPE.getName(), nonFunctional.getNonFunctionalRequirementTypeDTO().getName(), true) );
   		}
   	}
   	
   	/* DESIGN */
   	for (InterfaceSpecDTO specDTO : asset.getSolution().getDesign().getInterfaceSpecDTOs()) {
   		if (specDTO != null) {
   			terms.addAll( extractTerms(SolrField.INTERFACE_SPEC.getName(), specDTO.getName(), false) );
   			if (specDTO.getOperationDTOs().size() > 0) {
   				OperationDTO operationDTO = specDTO.getOperationDTOs().get(0);
   				if (operationDTO != null) {
   					terms.addAll( extractTerms(SolrField.OPERATION_NAME.getName(), operationDTO.getName(), false) );   					
   				}
   			}
   		}
   		
   	}
   	
   	/* IMPLEMENTATION */
   	for (ProgrammingLanguageDTO progrLang : asset.getSolution().getImplementation().getProgrammingLanguageDTOs()) {
   		if (progrLang != null) {
   			terms.addAll( extractTerms(SolrField.PROGRAMMING_LANGUAGE.getName(), progrLang.getName(), true) );
   		}
   		if (FieldsUtil.isValidProgrammingLanguage(progrLang) &&
					progrLang.getName().equalsIgnoreCase("Other")) {
				terms.addAll( extractTerms(SolrField.OTHER_PROGRAMMING_LANGUAGE.getName(), asset.getSolution().getImplementation().getOtherProgrammingLanguage(), true) );
			}
   	}
   	for (DesignPatternDTO designPatternDTO : asset.getSolution().getImplementation().getDesignPatternDTOs()) {
   		if (designPatternDTO != null) {
   			terms.addAll( extractTerms(SolrField.DESIGN_PATTERN.getName(), designPatternDTO.getName(), true) );
   		}
   	}
   	
   	for (SourceCodeDTO sourceCodeDTO : asset.getSolution().getImplementation().getSourceCodeDTOs()) {
   		if (sourceCodeDTO != null) {
   			if (sourceCodeDTO.getSourceCodeTypeDTO() != null) {
   				terms.addAll( extractTerms(SolrField.SOURCE_CODE_TYPE.getName(), sourceCodeDTO.getName(), true) );
   			}
   			if (sourceCodeDTO.getSoftwareLicenseDTO() != null) {
   				terms.addAll( extractTerms(SolrField.SOFTWARE_LICENSE.getName(), sourceCodeDTO.getName(), true) );
   			}
   		}
   	}
   	
   	for (UserInterfaceDTO uiDTO : asset.getSolution().getImplementation().getUserInterfaceDTOs()) {
   		if (uiDTO != null) {
   			if (uiDTO.getUserInterfaceTypeDTO() != null) {
   				terms.addAll( extractTerms(SolrField.USER_INTERFACE_TYPE.getName(), uiDTO.getName(), true) );
   			}
   		}
   	}
   	
   	/* TEST */
   	for (TestTypeDTO testTypeDTO : asset.getSolution().getTest().getTestTypeDTOs()) {
   		if (testTypeDTO != null) {
   			terms.addAll( extractTerms(SolrField.TEST_TYPE.getName(), testTypeDTO.getName(), true) );
   		}
   	}
   	for (TestMethodTypeDTO testMethodTypeDTO : asset.getSolution().getTest().getTestMethodTypeDTOs()) {
   		if (testMethodTypeDTO != null) {
   			terms.addAll( extractTerms(SolrField.TEST_METHOD_TYPE.getName(), testMethodTypeDTO.getName(), true) );
   		}
   	}
   	for (ProgrammingLanguageDTO progrLang : asset.getSolution().getTest().getProgrammingLanguageDTOs()) {
   		if (progrLang != null) {
   			terms.addAll( extractTerms(SolrField.PROGRAMMING_LANGUAGE.getName(), progrLang.getName(), true) );
   			if (FieldsUtil.isValidProgrammingLanguage(progrLang) &&
   					progrLang.getName().equalsIgnoreCase("Other")) {
   				terms.addAll( extractTerms(SolrField.OTHER_PROGRAMMING_LANGUAGE.getName(), asset.getSolution().getTest().getOtherProgrammingLanguage(), true) );
   			}
   		}
   	}
   	for (DesignPatternDTO designPatternDTO : asset.getSolution().getTest().getDesignPatternDTOs()) {
   		if (designPatternDTO != null) {
   			terms.addAll( extractTerms(SolrField.DESIGN_PATTERN.getName(), designPatternDTO.getName(), true) );
   		}
   	}
   	for (RelatedAsset relatedAsset : asset.getRelatedAssets()) {
   		if (relatedAsset != null) {
   			terms.addAll( extractTerms(SolrField.RELATED_ASSET_ID.getName(), relatedAsset.getId(), true) );
   			terms.addAll( extractTerms(SolrField.RELATED_ASSET_NAME.getName(), relatedAsset.getName(), false) );
	   	   	terms.addAll( extractTerms(SolrField.RELATED_ASSET_VERSION.getName(), relatedAsset.getVersion(), false) );
	   	   	if (relatedAsset.getRelatedAssetTypeDTO() != null) {
	   	   		terms.addAll( extractTerms(SolrField.RELATED_ASSET_TYPE.getName(), relatedAsset.getRelatedAssetTypeDTO().getName(), true) );	
	   	   	}
   		}
   	}
   	
   	if (terms.size() == 0) {
   		logger.warn("term list is empty!");
   	} else {
   		logger.info("terms: " + terms);
   	}
   	return terms;
   }
   
   private static List<Term> extractTerms(String fieldName, String value, boolean doubleQuote) {
   	if ((value == null) || (value.trim().length() == 0)) {
   		return new ArrayList<Term>();
   	}
   	if (fieldName == null) {
   		throw new RuntimeException("null fieldName");
   	}
   	if (doubleQuote) {
   		/* Value between double quotes */
   		List<Term> terms = new ArrayList<Term>();
   		terms.add( new Term(fieldName, '\"' + value + '\"') );
   		return terms;
		}
   	else {
   		/* Value between wildcards */
   		List<Term> terms = new ArrayList<Term>();
      	Scanner sc = new Scanner(value);
      	while (sc.hasNext()) {
   			terms.add(new Term(fieldName, "*" + sc.next() + "*"));
      	}
      	return terms;
		}
   }
   
   private static String fromTermsToQuery(List<Term> terms) {
		if (terms.size() == 0) {
			throw new IllegalArgumentException("Empty terms.");
		}
		String query = "";
		for (Term t : terms) {
			query += t.toString() + " OR ";
		}
		return query.substring(0, query.lastIndexOf(" OR "));
	}
   
   private static String fromTermsToQueryText(List<Term> terms) {
		if (terms.size() == 0) {
			throw new IllegalArgumentException("Empty terms.");
		}
		String query = "";
		
		for (Term t : terms) {
			query += t.text();
			float boost = SolrField.getFieldByName(t.field()).getBoost();
			if (boost != SolrFieldBoost.DEFAULT_BOOST) {
				query += "^" + boost;
			}
			query += " OR ";
		}
		return query.substring(0, query.lastIndexOf(" OR "));
	}
   
	private static void addArtifactable(SolrInputDocument doc, Artifactable artifactable) {
		addNotNull(doc, SolrField.ARTIFACT_NAME.getName(), artifactable.getName());
		addNotNull(doc, SolrField.REFERENCE.getName(), artifactable.getReference());
		addNotNull(doc, SolrField.ARTIFACT_DESCRIPTION.getName(), artifactable.getDescription());
		if (artifactable.getSoftwareLicenseDTO() != null) {
			addNotNull(doc, SolrField.SOFTWARE_LICENSE.getName(), artifactable.getSoftwareLicenseDTO().getName());
		}
		
		addFileNotNull(doc, artifactable);
		
		if (artifactable instanceof ArtifactDTO ||
				artifactable instanceof UseCaseDTO ||
				artifactable instanceof WsdlDTO) {
			//nothing
		}
		else if (artifactable instanceof FunctionalRequirementDTO) {
			if ( ((FunctionalRequirementDTO) artifactable).getFunctionalRequirementTypeDTO() != null) {
				addNotNull(doc,
								SolrField.FUNCTIONAL_REQUIREMENT_TYPE.getName(),
								((FunctionalRequirementDTO) artifactable).getFunctionalRequirementTypeDTO().getName());
			}
		}
		else if (artifactable instanceof NonFunctionalRequirementDTO) {
			if ( ((NonFunctionalRequirementDTO) artifactable).getNonFunctionalRequirementTypeDTO() != null) {
				addNotNull(doc,
								SolrField.NON_FUNCTIONAL_REQUIREMENT_TYPE.getName(),
								((NonFunctionalRequirementDTO) artifactable).getNonFunctionalRequirementTypeDTO().getName());
			}
		}
		else if (artifactable instanceof SourceCodeDTO) {
			if ( ((SourceCodeDTO) artifactable).getSourceCodeTypeDTO() != null) {
				addNotNull(doc,
								SolrField.SOURCE_CODE_TYPE.getName(),
								((SourceCodeDTO) artifactable).getSourceCodeTypeDTO().getName());
			}
		}
		else if (artifactable instanceof UserInterfaceDTO) {
			if ( ((UserInterfaceDTO) artifactable).getUserInterfaceTypeDTO() != null) {
				addNotNull(doc,
								SolrField.USER_INTERFACE_TYPE.getName(),
								((UserInterfaceDTO) artifactable).getUserInterfaceTypeDTO().getName());
			}
		}
		else {
			throw new IllegalArgumentException("Class type of artifact unrecognized: " + artifactable.getClass());
		}
	}
   
   private static void addNotNull(SolrInputDocument doc, String id, Object o) {
		if (o != null) {
			if (o instanceof String) {
				if ( ((String) o).length() > 0) {
					doc.addField(id, o);
				}
			}
			else {
				doc.addField(id, o);
			}
		}
	}
   
}
