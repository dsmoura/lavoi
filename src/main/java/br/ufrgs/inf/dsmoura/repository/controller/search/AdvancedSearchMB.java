package br.ufrgs.inf.dsmoura.repository.controller.search;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.asset.NavigationMB;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrConversionUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.ConsumptionDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InterfaceSpecDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InternationalizationTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationalSystemTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OrganizationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProjectDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAsset;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceDTO;


@KeepAlive
public class AdvancedSearchMB extends SearchMB {
	private static final long serialVersionUID = 1L;
	
	final Log logger = LogFactory.getLog(getClass());
	
	private String advancedQuery;
	
	private Asset asset;
	
	private ApplicationDomain applicationDomainAux;
	private ApplicationSubdomain applicationSubdomainAux;
	private OrganizationDTO organizationDTOAux;
	private ProjectDTO projectDTOAux;
	private DesignPatternDTO designPatternDTOAux;
	private ProgrammingLanguageDTO programmingLanguageDTOAux;
	private InternationalizationTypeDTO internationalizationTypeDTOAux;
	private TestMethodTypeDTO testMethodTypeDTOAux;
	private TestTypeDTO testTypeDTOAux;
	private OperationalSystemTypeDTO operationalSystemTypeDTOAux;
	private RelatedAsset relatedAssetAux;
	private InterfaceSpecDTO interfaceSpecDTOAUx;
	private OperationDTO operationDTOAux;
	private SourceCodeDTO sourceCodeDTOAux;
	private UserInterfaceDTO userInterfaceDTOAux;
	private FunctionalRequirementDTO functionalRequirementDTOAux;
	private NonFunctionalRequirementDTO nonFunctionalRequirementDTOAux;
	private UserDTO consumerUserDTOAux;
	
	{
		reinitSearch();
	}
	
	public String advancedSearch() {
		super.reinitSearch();
		prepareToAdvancedSearch();
		try {
			advancedSearchQuery();
		}
		catch(IllegalArgumentException iAE) {
			FacesContext.getCurrentInstance().addMessage("termToSearchID", new FacesMessage("Enter the search terms."));
			return clearResults();
		}
		return NavigationMB.ADVANCED_SEARCH;
	}
	
	/** @throws IllegalArgumentException */
	private void advancedSearchQuery() {
		this.advancedQuery = SolrConversionUtil.fromAssetToQuery(asset);
		if (this.advancedQuery.trim().isEmpty()) {
			throw new IllegalArgumentException("There are not search terms.");
		}
		this.saveUserQuery(advancedQuery);
		searchTime = System.currentTimeMillis();
		assetResultList =  this.queryDismax(this.advancedQuery, indexCurrentStart, pageSize);
		searchTime = System.currentTimeMillis() - searchTime;
	}
	
	public String newAdvancedSearch() {
		reinitSearch();
		return NavigationMB.ADVANCED_SEARCH;
	}
	
	public String nextPage() {
		indexCurrentStart += pageSize;
		advancedSearchQuery();
		return "";
	}
	
	public String previousPage() {
		indexCurrentStart -= pageSize;
		advancedSearchQuery();
		return "";
	}
	
	private void prepareToAdvancedSearch() {
		/* Application Domains */
		this.asset.getClassification().getApplicationSubdomains().clear();
		if (applicationDomainAux != null &&
				applicationSubdomainAux == null) {
			ApplicationSubdomain applicationSubdomain = new ApplicationSubdomain();
			applicationSubdomain.setApplicationDomain(applicationDomainAux);
			this.asset.getClassification().getApplicationSubdomains().add(applicationSubdomain);
		} else if (applicationSubdomainAux != null) {
			this.asset.getClassification().getApplicationSubdomains().add(applicationSubdomainAux);
		}
		/* Projects */
		this.asset.getClassification().getProjectDTOs().clear();
		if (organizationDTOAux != null &&
				projectDTOAux == null) {
			ProjectDTO proj = new ProjectDTO();
			proj.setOrganizationDTO(organizationDTOAux);
			this.asset.getClassification().getProjectDTOs().add(proj);
		} else if (projectDTOAux != null) {
			this.asset.getClassification().getProjectDTOs().add(projectDTOAux);
		}
		/* Internationalization */
		this.asset.getSolution().getRequirements().getInternationalizationTypeDTOs().clear();
		if (internationalizationTypeDTOAux != null) {
			this.asset.getSolution().getRequirements().getInternationalizationTypeDTOs().add(internationalizationTypeDTOAux);
		}
		/* Operational System */
		this.asset.getSolution().getRequirements().getOperationalSystemTypeDTOs().clear();
		if (operationalSystemTypeDTOAux != null) {
			this.asset.getSolution().getRequirements().getOperationalSystemTypeDTOs().add(operationalSystemTypeDTOAux);
		}
		/* Programming Languages */
		this.asset.getSolution().getImplementation().getProgrammingLanguageDTOs().clear();
		if (programmingLanguageDTOAux != null) {
			this.asset.getSolution().getImplementation().getProgrammingLanguageDTOs().add(programmingLanguageDTOAux);
		}
		/* Design Patterns */
		this.asset.getSolution().getImplementation().getDesignPatternDTOs().clear();
		if (designPatternDTOAux != null) {
			this.asset.getSolution().getImplementation().getDesignPatternDTOs().add(designPatternDTOAux);
		}
		/* Test Type */
		this.asset.getSolution().getTest().getTestTypeDTOs().clear();
		if (testTypeDTOAux != null) {
			this.asset.getSolution().getTest().getTestTypeDTOs().add(testTypeDTOAux);
		}
		/* Test Method Type */
		this.asset.getSolution().getTest().getTestMethodTypeDTOs().clear();
		if (testMethodTypeDTOAux != null) {
			this.asset.getSolution().getTest().getTestMethodTypeDTOs().add(testMethodTypeDTOAux);
		}
		/* Related Assets */
		this.asset.getRelatedAssets().clear();
		if (relatedAssetAux != null) {
			this.asset.getRelatedAssets().add(relatedAssetAux);
		}
		/* Interface Spec */
		this.asset.getSolution().getDesign().getInterfaceSpecDTOs().clear();
		if (interfaceSpecDTOAUx != null) {
			this.asset.getSolution().getDesign().getInterfaceSpecDTOs().add(interfaceSpecDTOAUx);
			if (operationDTOAux != null) {
				this.asset.getSolution().getDesign().getInterfaceSpecDTOs().get(0).getOperationDTOs().add(operationDTOAux);
			}
		}
		/* Source Code Type */
		/* Software License */
		this.asset.getSolution().getImplementation().getSourceCodeDTOs().clear();
		if (sourceCodeDTOAux != null) {
			this.asset.getSolution().getImplementation().getSourceCodeDTOs().add(sourceCodeDTOAux);
		}
		/* User Interface Type */
		this.asset.getSolution().getImplementation().getUserInterfaceDTOs().clear();
		if (userInterfaceDTOAux != null) {
			this.asset.getSolution().getImplementation().getUserInterfaceDTOs().add(userInterfaceDTOAux);
		}
		/* Functional Requirement Type */
		this.asset.getSolution().getRequirements().getFunctionalRequirementDTOs().clear();
		if (functionalRequirementDTOAux != null) {
			this.asset.getSolution().getRequirements().getFunctionalRequirementDTOs().add(functionalRequirementDTOAux);
		}
		/* Non Functional Requirement Type */
		this.asset.getSolution().getRequirements().getNonFunctionalRequirementDTOs().clear();
		if (nonFunctionalRequirementDTOAux != null) {
			this.asset.getSolution().getRequirements().getNonFunctionalRequirementDTOs().add(nonFunctionalRequirementDTOAux);
		}
		/* Consumers */
		this.asset.getUsage().getConsumptionDTOs().clear();
		if (consumerUserDTOAux != null) {
			ConsumptionDTO consumptionDTO = new ConsumptionDTO();
			consumptionDTO.setConsumerUserDTO(consumerUserDTOAux);
			this.asset.getUsage().getConsumptionDTOs().add(consumptionDTO);
		}
	}
	
	protected void reinitSearch() {
		super.reinitSearch();
		
		this.advancedQuery = "";
		this.asset = new Asset();
		
		this.asset.getUsage().setAuthorUserDTO(new UserDTO());
		this.asset.getUsage().setCertifierUserDTO(new UserDTO());
		
		this.applicationDomainAux = new ApplicationDomain();
		this.applicationSubdomainAux = new ApplicationSubdomain();
		this.organizationDTOAux = new OrganizationDTO();
		this.projectDTOAux = new ProjectDTO();
		this.designPatternDTOAux = new DesignPatternDTO();
		this.programmingLanguageDTOAux = new ProgrammingLanguageDTO();
		this.internationalizationTypeDTOAux = new InternationalizationTypeDTO();
		this.operationalSystemTypeDTOAux = new OperationalSystemTypeDTO();
		this.testMethodTypeDTOAux = new TestMethodTypeDTO();
		this.testTypeDTOAux = new TestTypeDTO();
		this.relatedAssetAux = new RelatedAsset();
		this.interfaceSpecDTOAUx = new InterfaceSpecDTO();
		this.operationDTOAux = new OperationDTO();
		this.sourceCodeDTOAux = new SourceCodeDTO();
		this.userInterfaceDTOAux = new UserInterfaceDTO();
		this.functionalRequirementDTOAux = new FunctionalRequirementDTO();
		this.nonFunctionalRequirementDTOAux = new NonFunctionalRequirementDTO();
		this.consumerUserDTOAux = new UserDTO();
	}
	
	public Boolean getHasSearched() {
		if (this.advancedQuery.length() > 0) {
			return true;
		}
		return false;
	}
	
	public String getResultsMessage() {
		if (assetResultList.isEmpty()) {
			return "Your search did not match any results.";
		} else {
			return assetResultList.size() + " results (" + getSearchTime() + " seconds)";			
		}
	}
	
	public List<SelectItem> getApplicationSubdomainList() {
		if (applicationDomainAux == null) {
			applicationDomainAux = new ApplicationDomain();
		}
		if ((applicationDomainAux.getApplicationSubdomains() == null) ||
				(applicationDomainAux.getApplicationSubdomains().isEmpty())) {
			List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(new SelectItem(null, "Select application domain."));
			return list;
		}
		return JSFUtil.toSelectItem(applicationDomainAux.getApplicationSubdomains(), "name");
	}
	
	public List<SelectItem> getProjectDTOList() {
		if (organizationDTOAux == null) {
			organizationDTOAux = new OrganizationDTO();
		}
		if ((organizationDTOAux.getProjectDTOs() == null) ||
				(organizationDTOAux.getProjectDTOs().isEmpty())) {
			List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(new SelectItem(null, "Select organization."));
			return list;
		}
		return JSFUtil.toSelectItem(organizationDTOAux.getProjectDTOs(), "name");
	}
	
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public ApplicationDomain getApplicationDomainAux() {
		return applicationDomainAux;
	}

	public void setApplicationDomainAux(ApplicationDomain applicationDomainAux) {
		this.applicationDomainAux = applicationDomainAux;
	}

	public ApplicationSubdomain getApplicationSubdomainAux() {
		return applicationSubdomainAux;
	}

	public void setApplicationSubdomainAux(
			ApplicationSubdomain applicationSubdomainAux) {
		this.applicationSubdomainAux = applicationSubdomainAux;
	}

	public OrganizationDTO getOrganizationDTOAux() {
		return organizationDTOAux;
	}

	public void setOrganizationDTOAux(OrganizationDTO organizationDTOAux) {
		this.organizationDTOAux = organizationDTOAux;
	}

	public ProjectDTO getProjectDTOAux() {
		return projectDTOAux;
	}

	public void setProjectDTOAux(ProjectDTO projectDTOAux) {
		this.projectDTOAux = projectDTOAux;
	}

	public DesignPatternDTO getDesignPatternDTOAux() {
		return designPatternDTOAux;
	}

	public void setDesignPatternDTOAux(DesignPatternDTO designPatternDTOAux) {
		this.designPatternDTOAux = designPatternDTOAux;
	}

	public ProgrammingLanguageDTO getProgrammingLanguageDTOAux() {
		return programmingLanguageDTOAux;
	}

	public void setProgrammingLanguageDTOAux(
			ProgrammingLanguageDTO programmingLanguageDTOAux) {
		this.programmingLanguageDTOAux = programmingLanguageDTOAux;
	}

	public InternationalizationTypeDTO getInternationalizationTypeDTOAux() {
		return internationalizationTypeDTOAux;
	}

	public void setInternationalizationTypeDTOAux(
			InternationalizationTypeDTO internationalizationTypeDTOAux) {
		this.internationalizationTypeDTOAux = internationalizationTypeDTOAux;
	}

	public OperationalSystemTypeDTO getOperationalSystemTypeDTOAux() {
		return operationalSystemTypeDTOAux;
	}

	public void setOperationalSystemTypeDTOAux(
			OperationalSystemTypeDTO operationalSystemTypeDTOAux) {
		this.operationalSystemTypeDTOAux = operationalSystemTypeDTOAux;
	}
	
	public TestMethodTypeDTO getTestMethodTypeDTOAux() {
		return testMethodTypeDTOAux;
	}

	public void setTestMethodTypeDTOAux(TestMethodTypeDTO testMethodTypeDTOAux) {
		this.testMethodTypeDTOAux = testMethodTypeDTOAux;
	}

	public TestTypeDTO getTestTypeDTOAux() {
		return testTypeDTOAux;
	}

	public void setTestTypeDTOAux(TestTypeDTO testTypeDTOAux) {
		this.testTypeDTOAux = testTypeDTOAux;
	}

	public RelatedAsset getRelatedAssetAux() {
		return relatedAssetAux;
	}

	public void setRelatedAssetAux(RelatedAsset relatedAssetAux) {
		this.relatedAssetAux = relatedAssetAux;
	}

	public InterfaceSpecDTO getInterfaceSpecDTOAUx() {
		return interfaceSpecDTOAUx;
	}

	public void setInterfaceSpecDTOAUx(InterfaceSpecDTO interfaceSpecDTOAUx) {
		this.interfaceSpecDTOAUx = interfaceSpecDTOAUx;
	}
	
	public OperationDTO getOperationDTOAux() {
		return operationDTOAux;
	}

	public void setOperationDTOAux(OperationDTO operationDTOAux) {
		this.operationDTOAux = operationDTOAux;
	}

	public SourceCodeDTO getSourceCodeDTOAux() {
		return sourceCodeDTOAux;
	}

	public void setSourceCodeDTOAux(SourceCodeDTO sourceCodeDTOAux) {
		this.sourceCodeDTOAux = sourceCodeDTOAux;
	}

	public UserInterfaceDTO getUserInterfaceDTOAux() {
		return userInterfaceDTOAux;
	}

	public void setUserInterfaceDTOAux(UserInterfaceDTO userInterfaceDTOAux) {
		this.userInterfaceDTOAux = userInterfaceDTOAux;
	}

	public FunctionalRequirementDTO getFunctionalRequirementDTOAux() {
		return functionalRequirementDTOAux;
	}

	public void setFunctionalRequirementDTOAux(
			FunctionalRequirementDTO functionalRequirementDTOAux) {
		this.functionalRequirementDTOAux = functionalRequirementDTOAux;
	}

	public NonFunctionalRequirementDTO getNonFunctionalRequirementDTOAux() {
		return nonFunctionalRequirementDTOAux;
	}

	public void setNonFunctionalRequirementDTOAux(
			NonFunctionalRequirementDTO nonFunctionalRequirementDTOAux) {
		this.nonFunctionalRequirementDTOAux = nonFunctionalRequirementDTOAux;
	}

	public UserDTO getConsumerUserDTOAux() {
		return consumerUserDTOAux;
	}

	public void setConsumerUserDTOAux(UserDTO consumerUserDTOAux) {
		this.consumerUserDTOAux = consumerUserDTOAux;
	}

}
