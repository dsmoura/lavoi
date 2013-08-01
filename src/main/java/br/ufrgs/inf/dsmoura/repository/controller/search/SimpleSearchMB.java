package br.ufrgs.inf.dsmoura.repository.controller.search;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.asset.NavigationMB;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrConversionUtil;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrField;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetSolrDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ConsumptionDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TagDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;

@KeepAlive
public class SimpleSearchMB extends SearchMB {
	private static final long serialVersionUID = 1L;
	
	final Log logger = LogFactory.getLog(getClass());

	private String termToSearch;
	
	{
		super.reinitSearch();
		this.reinitSearch();
	}
	
	@PostConstruct
	public void init() {
		this.searchWithParameters();
	}
	
	private void searchWithParameters() {
		/* used for permalinks */
		String query = JSFUtil.getRequestParameter("q");
		if (StringUtils.isNotBlank(query)) {
			/* it's ok, both parameters were passed */
			this.termToSearch = query;
			this.saveUserQuery(this.termToSearch);
			String solrQuery = SolrConversionUtil.fromStringToQuery(this.termToSearch);
			this.indexCurrentStart = 0;
			this.searchTermDismax(solrQuery);
		}
	}
	
	public String getPermalinkURL() {
		return JSFUtil.getRequestBaseURL() +
				"/searchAssets.jsf?q=" + this.termToSearch;
	}
	
	public String searchByMenu() {
		termToSearch = JSFUtil.getRequestParameter("termToSearchMenuID");
		if (termToSearch == null || termToSearch.trim().length() == 0) {
			return NavigationMB.SEARCH_ASSETS;
		}
		this.saveUserQuery(termToSearch);
		String query = SolrConversionUtil.fromStringToQuery(termToSearch);
		indexCurrentStart = 0;
		return searchTermDismax(query);
	}
	
	public String simpleSearch() {
		if (termToSearch == null ||
				termToSearch.trim().length() == 0) {
			FacesContext.getCurrentInstance().addMessage("termToSearchID",
																			new FacesMessage("Enter the search terms."));
			return "";
		}
		termToSearch = JSFUtil.getRequestParameter("termToSearchID");
		this.saveUserQuery(termToSearch);
		String query = SolrConversionUtil.fromStringToQuery(termToSearch);
		indexCurrentStart = 0;
		return searchTermDismax(query);
	}
	
	public String searchByTag() {
		Asset asset = new Asset();
		String tagValueParam = JSFUtil.getRequestParameter("tagValueParam");
		String tagTypeToSearchParam = JSFUtil.getRequestParameter("tagTypeToSearchParam");
		
		SolrField solrField = SolrField.getFieldByName(tagTypeToSearchParam);
		if (solrField.equals( SolrField.TAG )) {
			TagDTO tagDTO = new TagDTO();
			tagDTO.setName(tagValueParam);
			asset.getClassification().getTagDTOs().add( tagDTO );
		}
		else if (solrField.equals( SolrField.NAME )) {
			asset.setName(tagValueParam);
			
		}
		else if (solrField.equals( SolrField.PROGRAMMING_LANGUAGE )) {
			ProgrammingLanguageDTO plDTO = new ProgrammingLanguageDTO();
			plDTO.setName(tagValueParam);
			asset.getSolution().getImplementation().getProgrammingLanguageDTOs().add(plDTO);
		}
		else if (solrField.equals( SolrField.APPLICATION_DOMAIN )) {
			ApplicationDomain applicationDomain = new ApplicationDomain();
			applicationDomain.setName(tagValueParam);
			ApplicationSubdomain applicationSubdomain = new ApplicationSubdomain();
			applicationSubdomain.setApplicationDomain(applicationDomain);
			asset.getClassification().getApplicationSubdomains().add(applicationSubdomain);
		}
		else {
			throw new IllegalArgumentException("Field not found: " + solrField.getName());
		}
		
		this.termToSearch = SolrConversionUtil.fromAssetToQuery(asset);
		indexCurrentStart = 0;
		searchTime = System.currentTimeMillis();
		assetResultList = this.queryDismax(this.termToSearch, this.indexCurrentStart, this.pageSize);
		searchTime = System.currentTimeMillis() - searchTime;
		return NavigationMB.SEARCH_ASSETS;
	}
	
	public String newSearch() {
		reinitSearch();
		return NavigationMB.SEARCH_ASSETS;
	}
	
	public String searchPublishedAssetByMeList() {
		Asset asset = new Asset();
		asset.getUsage().setAuthorUserDTO(new UserDTO());
		asset.getUsage().getAuthorUserDTO().setUsername( JSFUtil.getLoggedUserDTO().getUsername() );
		this.termToSearch = SolrConversionUtil.fromAssetToQuery(asset);
		indexCurrentStart = 0;
		searchTime = System.currentTimeMillis();
		assetResultList = this.queryDismax(this.termToSearch, this.indexCurrentStart, this.pageSize);
		searchTime = System.currentTimeMillis() - searchTime;
		return NavigationMB.SEARCH_ASSETS;
	}
	
	public String searchConsumedAssetByMeList() {
		Asset asset = new Asset();
		ConsumptionDTO consumptionDTO = new ConsumptionDTO();
		consumptionDTO.setConsumerUserDTO(new UserDTO());
		consumptionDTO.getConsumerUserDTO().setUsername( JSFUtil.getLoggedUserDTO().getUsername() );
		asset.getUsage().getConsumptionDTOs().add( consumptionDTO );
		this.termToSearch = SolrConversionUtil.fromAssetToQuery(asset);
		indexCurrentStart = 0;
		searchTime = System.currentTimeMillis();
		assetResultList = this.queryDismax(this.termToSearch, this.indexCurrentStart, this.pageSize);
		searchTime = System.currentTimeMillis() - searchTime;
		return NavigationMB.SEARCH_ASSETS;
	}
	
	public String searchAssetsForCertificationList() {
		Asset asset = new Asset();
		asset.setState( TypesDAO.getInstance().getReadyForReuseAssetStateType() );
		this.termToSearch = SolrConversionUtil.fromAssetToQuery(asset);
		indexCurrentStart = 0;
		searchTime = System.currentTimeMillis();
		assetResultList = this.queryDismax(this.termToSearch, this.indexCurrentStart, this.pageSize);
		searchTime = System.currentTimeMillis() - searchTime;
		return NavigationMB.SEARCH_ASSETS;
	}
	
	public String nextPage() {
		String query = SolrConversionUtil.fromStringToQuery(termToSearch);
		indexCurrentStart += pageSize;
		searchTermDismax(query);
		return "";
	}
	
	public String previousPage() {
		String query = SolrConversionUtil.fromStringToQuery(termToSearch);
		indexCurrentStart -= pageSize;
		searchTermDismax(query);
		return "";
	}
	
	private String searchTermDismax(String query) {
		if (query == null || query.trim().length() == 0) {
			return clearResults();
		}
		searchTime = System.currentTimeMillis();
		assetResultList = this.queryDismax(query, this.indexCurrentStart, this.pageSize);
		searchTime = System.currentTimeMillis() - searchTime;
		return NavigationMB.SEARCH_ASSETS;
	}
	
	protected void reinitSearch() {
		this.indexCurrentStart = 0;
		this.termToSearch = "";
		this.assetResultList = new ArrayList<AssetSolrDTO>();
	}
	
	public Boolean getHasSearched() {
		if (termToSearch != null &&
				termToSearch.trim().length() > 0 ) {
			return true;
		}
		return false;
	}
	
	public String getTermToSearch() {
		return termToSearch;
	}

	public void setTermToSearch(String termToSearch) {
		this.termToSearch = termToSearch;
	}

}
