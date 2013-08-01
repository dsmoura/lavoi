package br.ufrgs.inf.dsmoura.repository.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDependencyTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetStateType;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetType;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InternationalizationTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationalSystemTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OrganizationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAssetTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SoftwareLicenseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceTypeDTO;

public class ListMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	final Log logger = LogFactory.getLog(getClass());

	/* DATABASE LISTS */
	private List<AssetType> assetTypeValues;
	private List<AssetStateType> assetStateTypeValues;
	private List<ApplicationDomain> applicationDomainValues;
	private List<OrganizationDTO> organizationDTOValues;
	private List<DesignPatternDTO> designPatternDTOValues;
	private List<ProgrammingLanguageDTO> programmingLanguageDTOValues;
	private List<SourceCodeTypeDTO> sourceCodeTypeDTOValues;
	private List<UserInterfaceTypeDTO> userInterfaceTypeValues;
	private List<SoftwareLicenseDTO> softwareLicenseValues;
	private List<FunctionalRequirementTypeDTO> functionalRequirementTypeValues;
	private List<NonFunctionalRequirementTypeDTO> nonFunctionalRequirementTypeValues;
	private List<InternationalizationTypeDTO> internationalizationTypeValues;
	private List<OperationalSystemTypeDTO> operationalSystemTypeDTOValues;
	private List<TestTypeDTO> testTypeDTOValues;
	private List<TestMethodTypeDTO> testMethodTypeDTOValues;
	private List<RelatedAssetTypeDTO> relatedAssetTypeDTOValues;
	private List<ArtifactDependencyTypeDTO> artifactDependencyTypeDTOValues;
	
	private String repositorySubtitle;
	private String homePageURL;
	
	public List<SelectItem> getAssetTypeList() {
		if (assetTypeValues == null) {
			assetTypeValues = TypesDAO.getInstance().getAssetTypeList();
		}
		return JSFUtil.toSelectItem(assetTypeValues, "name");
	}
	
	public List<SelectItem> getAssetStateTypeList() {
		if (assetStateTypeValues == null) {
			assetStateTypeValues = TypesDAO.getInstance().getAssetStateTypeList();
		}
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		for (AssetStateType assetStateType : assetStateTypeValues) {
			if (assetStateType.getName().equalsIgnoreCase("Certified") ||
					assetStateType.getName().equalsIgnoreCase("Ready for Reuse") ||
					assetStateType.getName().equalsIgnoreCase("In Review") ||
					assetStateType.getName().equalsIgnoreCase("Discontinued")) {
				selectItemList.add(new SelectItem(assetStateType, assetStateType.getName(), "", true));				
			} else {
				selectItemList.add(new SelectItem(assetStateType, assetStateType.getName(), "", false));
			}
		}
		return selectItemList;
	}
	
	public List<SelectItem> getAssetStateTypeListUnblocked() {
		if (assetStateTypeValues == null) {
			assetStateTypeValues = TypesDAO.getInstance().getAssetStateTypeList();
		}
		return JSFUtil.toSelectItem(assetStateTypeValues, "name");
	}
	
	public List<SelectItem> getApplicationDomainList() {
		if (applicationDomainValues == null) {
			applicationDomainValues = TypesDAO.getInstance().getApplicationsDomainList();
		}
		return JSFUtil.toSelectItem(applicationDomainValues, "name");
	}
	
	public List<SelectItem> getOrganizationDTOList() {
		if (organizationDTOValues == null) {
			organizationDTOValues = TypesDAO.getInstance().getOrganizationDTOList();
		}
		return JSFUtil.toSelectItem(organizationDTOValues, "name");
	}
	
	public List<SelectItem> getDesignPatternDTOList() {
		if (designPatternDTOValues == null) {
			designPatternDTOValues = TypesDAO.getInstance().getDesignPatternDTOList();
		}
		return JSFUtil.toSelectItem(designPatternDTOValues, "name");
	}
	
	public List<SelectItem> getProgrammingLanguageDTOList() {
		if (programmingLanguageDTOValues == null) {
			programmingLanguageDTOValues = TypesDAO.getInstance().getProgrammingLanguageDTOList();
		}
		return JSFUtil.toSelectItem(programmingLanguageDTOValues, "name");
	}
	
	public List<SelectItem> getSourceCodeTypeDTOList() {
		if (sourceCodeTypeDTOValues == null) {
			sourceCodeTypeDTOValues = TypesDAO.getInstance().getSourceCodeTypeDTOList();
		}
		return JSFUtil.toSelectItem(sourceCodeTypeDTOValues, "name");
	}
	
	public List<SelectItem> getUserInterfaceTypeDTOList() {
		if (userInterfaceTypeValues == null) {
			userInterfaceTypeValues = TypesDAO.getInstance().getUserInterfaceTypeDTOList();
		}
		return JSFUtil.toSelectItem(userInterfaceTypeValues, "name");
	}
	
	public List<SelectItem> getSoftwareLicenseDTOList() {
		return JSFUtil.toSelectItem(getSoftwareLicenseValues(), "name");
	}
	
	public List<SelectItem> getFunctionalRequirementTypeDTOList() {
		if (functionalRequirementTypeValues == null) {
			functionalRequirementTypeValues = TypesDAO.getInstance().getFunctionalRequirementTypeDTOList();
		}
		return JSFUtil.toSelectItem(functionalRequirementTypeValues, "name");
	}
	
	public List<SelectItem> getNonFunctionalRequirementTypeDTOList() {
		if (nonFunctionalRequirementTypeValues == null) {
			nonFunctionalRequirementTypeValues = TypesDAO.getInstance().getNonFunctionalRequirementTypeDTOList();
		}
		return JSFUtil.toSelectItem(nonFunctionalRequirementTypeValues, "name");
	}
	
	public List<SelectItem> getInternationalizationTypeDTOList() {
		if (internationalizationTypeValues == null) {
			internationalizationTypeValues = TypesDAO.getInstance().getInternationalizationTypeDTOList();
		}
		return JSFUtil.toSelectItem(internationalizationTypeValues, "name");
	}
	
	public List<SelectItem> getOperationalSystemTypeDTOList() {
		if (operationalSystemTypeDTOValues == null) {
			operationalSystemTypeDTOValues = TypesDAO.getInstance().getOperationalSystemTypeDTOList();
		}
		return JSFUtil.toSelectItem(operationalSystemTypeDTOValues, "name");
	}
	
	public List<SelectItem> getTestTypeDTOList() {
		if (testTypeDTOValues == null) {
			testTypeDTOValues = TypesDAO.getInstance().getTestTypeDTOList();
		}
		return JSFUtil.toSelectItem(testTypeDTOValues, "name");
	}
	
	public List<SelectItem> getTestMethodTypeDTOList() {
		if (testMethodTypeDTOValues == null) {
			testMethodTypeDTOValues = TypesDAO.getInstance().getTestMethodTypeDTOList();
		}
		return JSFUtil.toSelectItem(testMethodTypeDTOValues, "name");
	}
	
	public List<SelectItem> getRelatedAssetTypeDTOList() {
		if (relatedAssetTypeDTOValues == null) {
			relatedAssetTypeDTOValues = TypesDAO.getInstance().getRelatedAssetTypeDTOList();
		}
		return JSFUtil.toSelectItem(relatedAssetTypeDTOValues, "name");
	}
	
	public List<SelectItem> getArtifactDependencyTypeDTOList() {
		if (artifactDependencyTypeDTOValues == null) {
			artifactDependencyTypeDTOValues = TypesDAO.getInstance().getArtifactDependencyTypeDTOList();
		}
		return JSFUtil.toSelectItem(artifactDependencyTypeDTOValues, "name");
	}
	
	public List<AssetType> getAssetTypeValues() {
		if (assetTypeValues == null) {
			assetTypeValues = TypesDAO.getInstance().getAssetTypeList();
		}
		return assetTypeValues;
	}
	
	public List<AssetStateType> getAssetStateTypeValues() {
		if (assetStateTypeValues == null) {
			assetStateTypeValues = TypesDAO.getInstance().getAssetStateTypeList();
		}
		return assetStateTypeValues;
	}

	public void setAssetStateTypeValues(List<AssetStateType> assetStateTypeValues) {
		this.assetStateTypeValues = assetStateTypeValues;
	}

	public List<ApplicationDomain> getApplicationDomainValues() {
		if (applicationDomainValues == null) {
			applicationDomainValues = TypesDAO.getInstance().getApplicationsDomainList();
		}
		return applicationDomainValues;
	}

	public void setApplicationDomainValues(
			List<ApplicationDomain> applicationDomainValues) {
		this.applicationDomainValues = applicationDomainValues;
	}

	public List<OrganizationDTO> getOrganizationDTOValues() {
		if (organizationDTOValues == null) {
			organizationDTOValues = TypesDAO.getInstance().getOrganizationDTOList();
		}
		return organizationDTOValues;
	}

	public void setOrganizationDTOValues(List<OrganizationDTO> organizationDTOValues) {
		this.organizationDTOValues = organizationDTOValues;
	}

	public List<DesignPatternDTO> getDesignPatternDTOValues() {
		return designPatternDTOValues;
	}

	public void setDesignPatternDTOValues(
			List<DesignPatternDTO> designPatternDTOValues) {
		this.designPatternDTOValues = designPatternDTOValues;
	}

	public List<ProgrammingLanguageDTO> getProgrammingLanguageDTOValues() {
		if (programmingLanguageDTOValues == null) {
			programmingLanguageDTOValues = TypesDAO.getInstance().getProgrammingLanguageDTOList();
		}
		return programmingLanguageDTOValues;
	}

	public void setProgrammingLanguageDTOValues(
			List<ProgrammingLanguageDTO> programmingLanguageDTOValues) {
		this.programmingLanguageDTOValues = programmingLanguageDTOValues;
	}

	public List<SourceCodeTypeDTO> getSourceCodeTypeDTOValues() {
		return sourceCodeTypeDTOValues;
	}

	public void setSourceCodeTypeDTOValues(
			List<SourceCodeTypeDTO> sourceCodeTypeDTOValues) {
		this.sourceCodeTypeDTOValues = sourceCodeTypeDTOValues;
	}

	public List<UserInterfaceTypeDTO> getUserInterfaceTypeValues() {
		return userInterfaceTypeValues;
	}

	public void setUserInterfaceTypeValues(
			List<UserInterfaceTypeDTO> userInterfaceTypeValues) {
		this.userInterfaceTypeValues = userInterfaceTypeValues;
	}

	public List<SoftwareLicenseDTO> getSoftwareLicenseValues() {
		if (softwareLicenseValues == null) {
			softwareLicenseValues = TypesDAO.getInstance().getSoftwareLicenseDTOList();
			Collections.sort(softwareLicenseValues);
		}
		return softwareLicenseValues;
	}

	public void setSoftwareLicenseValues(List<SoftwareLicenseDTO> softwareLicenseValues) {
		this.softwareLicenseValues = softwareLicenseValues;
	}
	
	public List<SelectItem> getQualityScoresList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add( new SelectItem(null,"N/A") );
		list.add( new SelectItem(Integer.valueOf(0),"0") );
		list.add( new SelectItem(Integer.valueOf(1),"1") );
		list.add( new SelectItem(Integer.valueOf(2),"2") );
		list.add( new SelectItem(Integer.valueOf(3),"3") );
		list.add( new SelectItem(Integer.valueOf(4),"4") );
		list.add( new SelectItem(Integer.valueOf(5),"5") );
		return list;
	}
	
	public String getRepositorySubtitle() {
		if (repositorySubtitle == null) {
			try {
				repositorySubtitle = TypesDAO.getInstance().getSystemProperty(SystemPropertyEnum.REPOSITORY_SUBTITLE.getKey()).getValue();
			} catch (Exception e) {
				logger.warn("REPOSITORY_SUBTITLE system property was not found in database");
				repositorySubtitle = "";
			}
		}
		return repositorySubtitle;
	}
	
	public String getHomePageURL() {
		if (homePageURL == null) {
			try {
				homePageURL = TypesDAO.getInstance().getSystemProperty(SystemPropertyEnum.HOME_PAGE_URL.getKey()).getValue();
			} catch (Exception e) {
				logger.warn("HOME_PAGE_URL was not found in database");
				homePageURL = "";
			}
		}
		return homePageURL;
	}
}
