package br.ufrgs.inf.dsmoura.repository.controller.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.ufrgs.inf.dsmoura.repository.controller.asset.AnalysisMB;
import br.ufrgs.inf.dsmoura.repository.controller.asset.ArtifactType;
import br.ufrgs.inf.dsmoura.repository.controller.asset.DesignMB;
import br.ufrgs.inf.dsmoura.repository.controller.asset.ImplementationMB;
import br.ufrgs.inf.dsmoura.repository.controller.asset.RequirementsMB;
import br.ufrgs.inf.dsmoura.repository.controller.asset.TestMB;
import br.ufrgs.inf.dsmoura.repository.controller.asset.UsageMB;
import br.ufrgs.inf.dsmoura.repository.model.entity.AdjustmentDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDependencyDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.ConsumptionDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.DescriptorGroupDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FeedbackDTO;
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




public class XMLUtil {
	
	public static InputStream fromAssetToRassetXML(Asset asset) {

		// Order by RAS:
		// 1. profile
		// 2. description
		// 3. classification
		// 4. solution
		// 5. usage
		// 6. relatedAsset

		/* GENERAL */
		
		Element ecp = new Element("Asset");
		ecp.setAttribute("name", asset.getName());
		ecp.setAttribute("id", asset.getId());
		if (asset.getType() != null) {
			ecp.setAttribute("type", asset.getType().getName());
			if (FieldsUtil.isValidType(asset.getType()) &&
					asset.getType().getName().equalsIgnoreCase("Other")) {
				ecp.setAttribute("other_type", asset.getOtherType());
			}
		}
		if (asset.getState() != null) {
			ecp.setAttribute("state", asset.getState().getName());
		}
		if (asset.getSoftwareLicenseDTO() != null) {
			ecp.setAttribute("software_license", asset.getSoftwareLicenseDTO().getName());
			if (FieldsUtil.isValidSoftwareLicense(asset.getSoftwareLicenseDTO()) &&
					asset.getSoftwareLicenseDTO().getName().equalsIgnoreCase("Other")) {
				ecp.setAttribute("other_software_license", asset.getOtherSoftwareLicense());
			}
		}
		ecp.setAttribute("version", asset.getVersion());
		ecp.setAttribute("date", asset.getStrDate());
		ecp.setAttribute("creation_date", asset.getStrCreationDate());
		ecp.setAttribute("access_rights", asset.getAccessRights());
		ecp.setAttribute("short_description", asset.getShortDescription());
		
		Element description = new Element("description");
		description.addContent(asset.getDescription());
		ecp.addContent(description);

		
		/* CLASSIFICATION */
		
		Element classification = new Element("classification");
		ecp.addContent(classification);
		
		/* Application Domains */
		for (ApplicationSubdomain as : asset.getClassification().getApplicationSubdomains()) {
			Element applicationSubdomain = new Element("application_subdomain");
			applicationSubdomain.setAttribute("name", as.getName());
			
			Element applicationDomain = new Element("application_domain");
			applicationDomain.setAttribute("name", as.getApplicationDomain().getName());
			
			if (FieldsUtil.isValidApplicationSubdomain(as) &&
     				as.getName().equalsIgnoreCase("Other")) {
				applicationDomain.setAttribute("name", asset.getClassification().getOtherApplicationDomain());
				applicationSubdomain.setAttribute("name", asset.getClassification().getOtherApplicationSubdomain());
			}
			
			applicationSubdomain.addContent(applicationDomain);
			classification.addContent(applicationSubdomain);
		}
		
		/* Projects */
		for (ProjectDTO proj : asset.getClassification().getProjectDTOs()) {
			Element project = new Element("project");
			project.setAttribute("name", proj.getName());
			classification.addContent(project);

			Element organization = new Element("organization");
			organization.setAttribute("name", proj.getOrganizationDTO().getName());
			project.addContent(organization);
		}
		
		/* Tags */
		for (TagDTO t : asset.getClassification().getTagDTOs()) {
			Element tag = new Element("tag");
			tag.setAttribute("name", t.getName());
			classification.addContent(tag);
		}
		
		/* Group Descriptors */
		for (DescriptorGroupDTO groupDTO : asset.getClassification().getDescriptorGroupDTOs()) {
			Element group = new Element("descriptor_group");
			group.setAttribute("name", groupDTO.getName());
			classification.addContent(group);
			/* Descriptors */
			for (FreeFormDescriptorDTO descriptorDTO : groupDTO.getFreeFormDescriptorDTOs()) {
				Element descriptor = new Element("free_form_escriptor");
				descriptor.setAttribute("name", descriptorDTO.getName());
				descriptor.setAttribute("value", descriptorDTO.getFreeFormValue());
				group.addContent(descriptor);
			}
		}
		
		/* SOLUTION */
		
		Element solution = new Element("solution");
		ecp.addContent(solution);
		
		/* EFFORT */
		Element effort = new Element("effort");
		solution.addContent(effort);
		if (asset.getEffortDTO().getEstimatedTime() != null) {
			effort.setAttribute("estimated_time", asset.getEffortDTO().getEstimatedTime().toString());
		}
		if (asset.getEffortDTO().getRealTime() != null) {
			effort.setAttribute("real_time", asset.getEffortDTO().getRealTime().toString());
		}
		if (asset.getEffortDTO().getMonetary() != null) {
			effort.setAttribute("monetary", asset.getEffortDTO().getMonetary().toString());
		}
		if (asset.getEffortDTO().getTeamMembers() != null) {
			effort.setAttribute("team_members", asset.getEffortDTO().getTeamMembers().toString());
		}
		if (asset.getEffortDTO().getLinesOfCode() != null) {
			effort.setAttribute("linesOfCode", asset.getEffortDTO().getLinesOfCode().toString());
		}
		
		/* REQUIREMENTS */
		Element requirements = new Element("requirements");
		solution.addContent(requirements);
		
		requirements.addContent( fromArtifactsToElements(asset.getSolution().getRequirements().getFunctionalRequirementDTOs(),
																			RequirementsMB.FUNCTIONAL_REQ_REFERENCE_PATH) );
		
		requirements.addContent( fromArtifactsToElements(asset.getSolution().getRequirements().getUseCaseDTOs(),
																			RequirementsMB.USE_CASE_REFERENCE_PATH) );
		
		requirements.addContent( fromArtifactsToElements(asset.getSolution().getRequirements().getUserInterfaceDTOs(),
																			RequirementsMB.USER_INTERFACE_REFERENCE_PATH) );
		
		requirements.addContent( fromArtifactsToElements(asset.getSolution().getRequirements().getNonFunctionalRequirementDTOs(),
																			RequirementsMB.NON_FUNCTIONAL_REQ_REFERENCE_PATH) );
		
		/* Internationalization */
		for (InternationalizationTypeDTO i : asset.getSolution().getRequirements().getInternationalizationTypeDTOs()) {
			Element internat = new Element("language");
			internat.setAttribute("name", i.getName());
			requirements.addContent(internat);
		}
		
		/* Operational Systems */
		for (OperationalSystemTypeDTO o : asset.getSolution().getRequirements().getOperationalSystemTypeDTOs()) {
			Element operSyst = new Element("operational_system");
			operSyst.setAttribute("name", o.getName());
			requirements.addContent(operSyst);
		}
		
		
		/* ANALYSIS */
		
		Element analysis = new Element("analysis");
		solution.addContent(analysis);
		
		/* Interface Specs */
		for (InterfaceSpecDTO intSpecDTO : asset.getSolution().getAnalysis().getInterfaceSpecDTOs()) {
			Element interfaceSpec = new Element("interface_spec");
			if (intSpecDTO.getDescription() != null) {
				interfaceSpec.setAttribute("description", intSpecDTO.getDescription());
			}
			interfaceSpec.setAttribute("name", intSpecDTO.getName());
			analysis.addContent(interfaceSpec);
			/* Descriptors */
			for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
				Element operation = new Element("operation");
				operation.setAttribute("name", operationDTO.getName());
				operation.setAttribute("initiates_transaction", operationDTO.getInitiatesTransaction().toString());
				if (operationDTO.getDescription() != null) {
					operation.setAttribute("description", operationDTO.getDescription());
				}
				interfaceSpec.addContent(operation);
			}
		}
		
		/* Use Case */
		analysis.addContent( fromArtifactsToElements(asset.getSolution().getAnalysis().getUseCaseDTOs(),
																		AnalysisMB.USE_CASE_REFERENCE_PATH) );
		
		/* User Interfaces */
		analysis.addContent( fromArtifactsToElements(asset.getSolution().getAnalysis().getUserInterfaceDTOs(),
																		AnalysisMB.USER_INTERFACE_REFERENCE_PATH) );
		
		/* General Artifacts */
		analysis.addContent( fromArtifactsToElements(asset.getSolution().getAnalysis().getArtifactDTOs(),
																		AnalysisMB.GENERAL_ARTIFACT_REFERENCE_PATH) );
		
		/* DESIGN */
		
		Element design = new Element("design");
		solution.addContent(design);
		
		/* Interface Specs */
		for (InterfaceSpecDTO intSpecDTO : asset.getSolution().getDesign().getInterfaceSpecDTOs()) {
			Element interfaceSpec = new Element("interface_spec");
			interfaceSpec.setAttribute("name", intSpecDTO.getName());
			if (intSpecDTO.getDescription() != null) {
				interfaceSpec.setAttribute("description", intSpecDTO.getDescription());
			}
			design.addContent(interfaceSpec);
			/* Descriptors */
			for (OperationDTO operationDTO : intSpecDTO.getOperationDTOs()) {
				Element operation = new Element("operation");
				operation.setAttribute("name", operationDTO.getName());
				operation.setAttribute("initiates_transaction", operationDTO.getInitiatesTransaction().toString());
				if (operationDTO.getDescription() != null) {
					operation.setAttribute("description", operationDTO.getDescription());
				}
				interfaceSpec.addContent(operation);
			}
		}
		/* Design Patterns */
		for (DesignPatternDTO dp : asset.getSolution().getDesign().getDesignPatternDTOs()) {
			Element designPat = new Element("design_pattern");
			designPat.setAttribute("name", dp.getName());
			design.addContent(designPat);
		}
		
		/* User Interfaces */
		design.addContent( fromArtifactsToElements(asset.getSolution().getDesign().getUserInterfaceDTOs(),
													DesignMB.USER_INTERFACE_REFERENCE_PATH) );
		
		/* General Artifacts */
		design.addContent( fromArtifactsToElements(asset.getSolution().getDesign().getArtifactDTOs(),
													DesignMB.GENERAL_ARTIFACT_REFERENCE_PATH) );
		
		/* IMPLEMENTATION */
		
		Element implementation = new Element("implementation");
		solution.addContent(implementation);
		
		/* Programming Languages */
		for (ProgrammingLanguageDTO pl : asset.getSolution().getImplementation().getProgrammingLanguageDTOs()) {
			Element progLang = new Element("programming_language");
			progLang.setAttribute("name", pl.getName());
			if (FieldsUtil.isValidProgrammingLanguage(pl) &&
					pl.getName().equalsIgnoreCase("Other")) {
				progLang.setAttribute("other_name", asset.getSolution().getImplementation().getOtherProgrammingLanguage());
			}
			implementation.addContent(progLang);
		}
		
		/* Design Patterns */
		for (DesignPatternDTO dp : asset.getSolution().getImplementation().getDesignPatternDTOs()) {
			Element designPat = new Element("design_pattern");
			designPat.setAttribute("name", dp.getName());
			implementation.addContent(designPat);
		}
		
		/* Source Codes */
		implementation.addContent( fromArtifactsToElements(asset.getSolution().getImplementation().getSourceCodeDTOs(),
																ImplementationMB.SOURCE_CODE_REFERENCE_PATH) );
		
		/* User Interfaces */
		implementation.addContent( fromArtifactsToElements(asset.getSolution().getImplementation().getUserInterfaceDTOs(),
																ImplementationMB.USER_INTERFACE_REFERENCE_PATH) );
		
		/* WSDL */
		implementation.addContent( fromArtifactsToElements(asset.getSolution().getImplementation().getWsdlDTOs(),
																ImplementationMB.WSDL_REFERENCE_PATH) );
		
		/* General Artifacts */
		implementation.addContent( fromArtifactsToElements(asset.getSolution().getImplementation().getArtifactDTOs(),
																ImplementationMB.GENERAL_ARTIFACT_REFERENCE_PATH) );
		
		/* TEST */
		Element test = new Element("test");
		solution.addContent(test);
		
		/* Test Type */
		for (TestTypeDTO tt : asset.getSolution().getTest().getTestTypeDTOs()) {
			Element testMethod = new Element("test_type");
			testMethod.setAttribute("name", tt.getName());
			test.addContent(testMethod);
		}
		
		/* Test Method Type */
		for (TestMethodTypeDTO tmt : asset.getSolution().getTest().getTestMethodTypeDTOs()) {
			Element testMethod = new Element("test_method_type");
			testMethod.setAttribute("name", tmt.getName());
			test.addContent(testMethod);
		}
		
		/* Programming Languages */
		for (ProgrammingLanguageDTO pl : asset.getSolution().getTest().getProgrammingLanguageDTOs()) {
			Element progLang = new Element("programming_language");
			progLang.setAttribute("name", pl.getName());
			if (FieldsUtil.isValidProgrammingLanguage(pl) &&
					pl.getName().equalsIgnoreCase("Other")) {
				progLang.setAttribute("other_name", asset.getSolution().getTest().getOtherProgrammingLanguage());				
			}
			test.addContent(progLang);
		}
		
		/* Design Patterns */
		for (DesignPatternDTO dp : asset.getSolution().getTest().getDesignPatternDTOs()) {
			Element designPat = new Element("design_pattern");
			designPat.setAttribute("name", dp.getName());
			test.addContent(designPat);
		}
		
		/* Source Codes */
		test.addContent( fromArtifactsToElements(asset.getSolution().getTest().getSourceCodeDTOs(),
																TestMB.SOURCE_CODE_REFERENCE_PATH) );
		
		/* General Artifacts */
		test.addContent( fromArtifactsToElements(asset.getSolution().getTest().getArtifactDTOs(),
																TestMB.GENERAL_ARTIFACT_REFERENCE_PATH) );
		
		/* USAGE */
		
		Element usage = new Element("usage");
		ecp.addContent(usage);
		
		Element usageDescription = new Element("description");
		usageDescription.addContent(asset.getUsage().getDescription());
		usage.addContent(usageDescription);
		
		usage.addContent( fromArtifactsToElements(asset.getUsage().getArtifactDTOs(),
																	UsageMB.GENERAL_ARTIFACT_REFERENCE_PATH) );
		
		/* Author User */
		Element author = new Element("author");
		author.setAttribute("username", asset.getUsage().getAuthorUserDTO().getUsername());
		author.setAttribute("name", asset.getUsage().getAuthorUserDTO().getName());
		usage.addContent(author);
		
		usage.setAttribute("authorship_date", asset.getUsage().getStrAuthorshipDate());
		
		usage.setAttribute("creator_name", asset.getUsage().getCreatorUsername());
		
		/* Certifier User */
		if (asset.getUsage().getCertifierUserDTO() != null) {
			Element certifier = new Element("certifier");
			certifier.setAttribute("username", asset.getUsage().getCertifierUserDTO().getUsername());
			certifier.setAttribute("name", asset.getUsage().getCertifierUserDTO().getName());
			usage.addContent(certifier);			
		}
		if (asset.getUsage().getCertificationDate() != null) {
			usage.setAttribute("certification_date", asset.getUsage().getStrCertificationDate());
		}
		
		/* Feedbacks */
		for (FeedbackDTO feedbackDTO : asset.getFeedbackDTOs()) {
			if (feedbackDTO.getHasFeedback()) {
				Element feedback = new Element("feedback");
				feedback.setAttribute("date", feedbackDTO.getStrDate());
				feedback.setAttribute("comment", feedbackDTO.getComment());
				
				/* Feedback User */
				Element user = new Element("feedback_user");
				user.setAttribute("username", feedbackDTO.getFeedbackUserDTO().getUsername());
				user.setAttribute("name", feedbackDTO.getFeedbackUserDTO().getName());
				feedback.addContent(user);
				
				/* Scores */
				feedback.setAttribute("general_score", feedbackDTO.getGeneralScore().toString());
				
				Element qualityInUse = new Element("quality_in_use");
				if (feedbackDTO.getQualityInUseDTO().getContextCoverageScore() != null) {
					qualityInUse.setAttribute("context_coverage_score", feedbackDTO.getQualityInUseDTO().getContextCoverageScore().toString());						
				}
				if (feedbackDTO.getQualityInUseDTO().getEfficiencyScore() != null) {
					qualityInUse.setAttribute("efficiency_score", feedbackDTO.getQualityInUseDTO().getEfficiencyScore().toString());						
				}
				if (feedbackDTO.getQualityInUseDTO().getEffectivenessScore() != null) {
					qualityInUse.setAttribute("effectiveness_score", feedbackDTO.getQualityInUseDTO().getEffectivenessScore().toString());						
				}
				if (feedbackDTO.getQualityInUseDTO().getSafetyScore() != null) {
					qualityInUse.setAttribute("safety_score", feedbackDTO.getQualityInUseDTO().getSafetyScore().toString());						
				}
				if (feedbackDTO.getQualityInUseDTO().getSatisfactionScore() != null) {
					qualityInUse.setAttribute("satisfaction_score", feedbackDTO.getQualityInUseDTO().getSatisfactionScore().toString());						
				}
				feedback.addContent(qualityInUse);
				
				Element softProdQuality = new Element("software_product_quality");
				if (feedbackDTO.getSoftwareProductQualityDTO().getCompatibilityScore() != null) {
					softProdQuality.setAttribute("compatibility_score", feedbackDTO.getSoftwareProductQualityDTO().getCompatibilityScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getFunctionalSuitabilityScore() != null) {
					softProdQuality.setAttribute("functional_suitability_score", feedbackDTO.getSoftwareProductQualityDTO().getFunctionalSuitabilityScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getMaintainabilityScore() != null) {
					softProdQuality.setAttribute("maintainability_score", feedbackDTO.getSoftwareProductQualityDTO().getMaintainabilityScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getPerformanceEfficiencyScore() != null) {
					softProdQuality.setAttribute("performance_efficiency_score", feedbackDTO.getSoftwareProductQualityDTO().getPerformanceEfficiencyScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getPortabilityScore() != null) {
					softProdQuality.setAttribute("portability_score", feedbackDTO.getSoftwareProductQualityDTO().getPortabilityScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getReliabilityScore() != null) {
					softProdQuality.setAttribute("reliability_score", feedbackDTO.getSoftwareProductQualityDTO().getReliabilityScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getSecurityScore() != null) {
					softProdQuality.setAttribute("security_score", feedbackDTO.getSoftwareProductQualityDTO().getSecurityScore().toString());						
				}
				if (feedbackDTO.getSoftwareProductQualityDTO().getUsabilityScore() != null) {
					softProdQuality.setAttribute("usability_score", feedbackDTO.getSoftwareProductQualityDTO().getUsabilityScore().toString());						
				}
				feedback.addContent(softProdQuality);
				
				usage.addContent(feedback);
			}
		}
		
		/* Adjustments */
		for (AdjustmentDTO adjustmentDTO : asset.getUsage().getAdjustmentDTOs()) {
			Element adjustment = new Element("adjustment");
			adjustment.setAttribute("date", adjustmentDTO.getStrDate());
			Element user = new Element("adjuster_user");
			user.setAttribute("username", adjustmentDTO.getAdjusterUserDTO().getUsername());
			user.setAttribute("name", adjustmentDTO.getAdjusterUserDTO().getName());
			adjustment.addContent(user);
			Element adjustmentDescription = new Element("description");
			adjustmentDescription.addContent(adjustmentDTO.getDescription());
			adjustment.addContent(adjustmentDescription);
			usage.addContent(adjustment);
		}
		
		/* Consumptions */
		for (ConsumptionDTO consumptionDTO : asset.getUsage().getConsumptionDTOs()) {
			Element consumption = new Element("consumption");
			consumption.setAttribute("date", consumptionDTO.getStrDate());
			Element user = new Element("comsumer_user");
			user.setAttribute("username", consumptionDTO.getConsumerUserDTO().getUsername());
			user.setAttribute("name", consumptionDTO.getConsumerUserDTO().getName());
			consumption.addContent(user);
			usage.addContent(consumption);
		}
		
		/* User Comments */
		for (UserCommentDTO userCommentDTO : asset.getUsage().getUserCommentDTOs()) {
			Element consumption = new Element("user_comment");
			consumption.setAttribute("date", userCommentDTO.getStrDate());
			Element user = new Element("user");
			user.setAttribute("username", userCommentDTO.getUserDTO().getUsername());
			user.setAttribute("name", userCommentDTO.getUserDTO().getName());
			consumption.addContent(user);
			Element comment = new Element("comment");
			comment.addContent(userCommentDTO.getComment());
			consumption.addContent(comment);
			usage.addContent(consumption);
		}
		
		/* RELATED ASSETS */
		
		Element relatedAssets = new Element("related_assets");
		ecp.addContent(relatedAssets);
		for (RelatedAsset ra : asset.getRelatedAssets()) {
			Element relatedAsset = new Element("related_asset");
			relatedAsset.setAttribute("id", ra.getId());
			relatedAsset.setAttribute("name", ra.getName());
			relatedAsset.setAttribute("version", ra.getVersion());
			relatedAsset.setAttribute("reference", ra.getReference());
			if (ra.getRelatedAssetTypeDTO() != null) {
				relatedAsset.setAttribute("relationshipType", ra.getRelatedAssetTypeDTO().getName());
			}
			relatedAssets.addContent(relatedAsset);
		}
		
		/* Generate XML */
		Document doc = new Document();
		doc.setRootElement(ecp);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
		try {
			xmlOut.output(doc, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
		
		return is;
	}
	
	private static List<Element> fromArtifactsToElements(List<? extends Artifactable> artifacts, String referencePath) {
		List<Element> elements = new ArrayList<Element>();
		for (Artifactable artifactable : artifacts) {
			Element element = null;
			
			/* element name */
			if (artifactable instanceof ArtifactDTO) {
				element = new Element(ArtifactType.GENERAL.getXmlName());
			}
			else if (artifactable instanceof FunctionalRequirementDTO) {
				element = new Element(ArtifactType.FUNCTIONAL_REQ.getXmlName());
			}
			else if (artifactable instanceof NonFunctionalRequirementDTO) {
				element = new Element(ArtifactType.NON_FUNCTIONAL_REQ.getXmlName());
			}
			else if (artifactable instanceof SourceCodeDTO) {
				element = new Element(ArtifactType.SOURCE_CODE.getXmlName());
			}
			else if (artifactable instanceof UserInterfaceDTO) {
				element = new Element(ArtifactType.USER_INTERFACE.getXmlName());
			}
			else if (artifactable instanceof UseCaseDTO) {
				element = new Element(ArtifactType.USE_CASE.getXmlName());
			}
			else if (artifactable instanceof WsdlDTO) {
				element = new Element(ArtifactType.WSDL.getXmlName());
			}
			else {
				throw new IllegalArgumentException("Class type of artifact unrecognized: " + artifactable.getClass());
			}
			
			/* id and name */
			element.setAttribute("id", artifactable.getId());
			element.setAttribute("name", artifactable.getName());
			
			Element description = new Element("description");
			description.addContent(artifactable.getDescription());
			element.addContent(description);
			
			/* artifact types */
			if (artifactable instanceof ArtifactDTO ||
					artifactable instanceof UseCaseDTO ||
					artifactable instanceof WsdlDTO) {
				//nothing
			}
			else if (artifactable instanceof FunctionalRequirementDTO) {
				if ( ((FunctionalRequirementDTO) artifactable).getFunctionalRequirementTypeDTO() != null) {
					element.setAttribute("functional_requirement_type",
													( (FunctionalRequirementDTO) artifactable).getFunctionalRequirementTypeDTO().getName());
				}
			}
			else if (artifactable instanceof NonFunctionalRequirementDTO) {
				if ( ((NonFunctionalRequirementDTO) artifactable).getNonFunctionalRequirementTypeDTO() != null) {
					element.setAttribute("non_functional_requirement_type",
													( (NonFunctionalRequirementDTO) artifactable).getNonFunctionalRequirementTypeDTO().getName());
				}
			}
			else if (artifactable instanceof SourceCodeDTO) {
				if ( ((SourceCodeDTO) artifactable).getSourceCodeTypeDTO() != null) {
					element.setAttribute("source_code_type",
													( (SourceCodeDTO) artifactable).getSourceCodeTypeDTO().getName());
				}
			}
			else if (artifactable instanceof UserInterfaceDTO) {
				if ( ((UserInterfaceDTO) artifactable).getUserInterfaceTypeDTO() != null) {
					element.setAttribute("user_interface_type",
													( (UserInterfaceDTO) artifactable).getUserInterfaceTypeDTO().getName());
				}
			}
			else {
				throw new IllegalArgumentException("Class type of artifact unrecognized: " + artifactable.getClass());
			}
			
			/* software license */
			if (artifactable.getSoftwareLicenseDTO() != null) {
				element.setAttribute("software_license", artifactable.getSoftwareLicenseDTO().getName());					
			}
			
			/* reference */
			if (artifactable.getFile() == null) {
				element.setAttribute("reference", artifactable.getReference());
			} else {
				element.setAttribute("reference", referencePath + "\\" + artifactable.getName());
			}
			
			/* artifact dependency */
			for (ArtifactDependencyDTO artifactDependencyDTO : artifactable.getArtifactDependencyDTOs()) {
				Element artifactDependency = new Element("artifact_dependency");
				artifactDependency.setAttribute("artifact_id", artifactDependencyDTO.getArtifactID());
				if (artifactDependencyDTO.getArtifactDependencyTypeDTO() != null) {
					artifactDependency.setAttribute("artifact_dependency_type",
																artifactDependencyDTO.getArtifactDependencyTypeDTO().getName());
				}
				element.addContent(artifactDependency);
			}
			
			elements.add(element);
		}
		return elements;
	}
}