package br.ufrgs.inf.dsmoura.repository.controller.search;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;


import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrDocumentList;

import br.ufrgs.inf.dsmoura.repository.controller.solr.SearchOrder;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrConversionUtil;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrServerUtil;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.GenericDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetSolrDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAsset;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserQueryLogDTO;

@KeepAlive
public class SearchMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final int NUMBER_ASSETS_LIST = 10;
	
	final Log logger = LogFactory.getLog(getClass());
	
	protected long searchTime;
	
	/* pagination */
	protected final Integer pageSize = 15;
	protected Integer indexCurrentStart;
	protected Long numFound;
	
	protected Float maxScore;

	protected SearchOrder searchOrder;
	
	protected List<AssetSolrDTO> assetResultList = new ArrayList<AssetSolrDTO>();
	
	private List<AssetSolrDTO> suggestedAssetsList;
	private List<AssetSolrDTO> bestScoredAssetsList;
	private List<AssetSolrDTO> mostReusedAssetsList;
	private List<AssetSolrDTO> lastCertifiedAssetsList;
	private List<AssetSolrDTO> lastPublishedAssetsList;
	private Long publishedAssetsNumber;
	
	{
		reinitSearch();
	}
	
	protected void reinitSearch() {
		this.indexCurrentStart = 0;
		this.assetResultList = new ArrayList<AssetSolrDTO>();
		this.searchOrder = SearchOrder.BY_RELEVANCE;
	}
	
	public List<SelectItem> getSearchOrderList() {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		selectItemList.add(new SelectItem(SearchOrder.BY_RELEVANCE));
		selectItemList.add(new SelectItem(SearchOrder.BY_NAME));
		selectItemList.add(new SelectItem(SearchOrder.BY_LAST_PUBLISHED));
		selectItemList.add(new SelectItem(SearchOrder.BY_MOST_REUSED));
		selectItemList.add(new SelectItem(SearchOrder.BY_BEST_RATED));
		return selectItemList;
	}
	
	public List<AssetSolrDTO> searchAnotherVersions(Asset asset) {
		Asset assetWithID = new Asset();
		assetWithID.setId( asset.getId() );
		List<AssetSolrDTO> anotherVersionsAssetList = advancedSearchQuery(assetWithID);
		/* remove the same version */
		for (int i = anotherVersionsAssetList.size()-1; i >= 0; i--) {
			if (anotherVersionsAssetList.get(i).getVersion().equalsIgnoreCase(asset.getVersion())) {
				anotherVersionsAssetList.remove(i);
			}
		}
		Collections.sort(anotherVersionsAssetList);
		return anotherVersionsAssetList;
	}
	
	public void searchSuggestedAssets(Asset asset, int rowsSize) {
		String suggestedQuery = SolrConversionUtil.fromAssetToTextQuery(asset);
		suggestedAssetsList =  this.queryDismax(suggestedQuery, 0, rowsSize);
		
		/* remove the same asset (id+version) */
		for (int i = suggestedAssetsList.size()-1; i >= 0; i--) {
			if ((asset.getAssetPk() != null) &&
					(suggestedAssetsList.get(i).getAssetPk().equalsIgnoreCase(asset.getAssetPk().toString()))) {
				suggestedAssetsList.remove(i);
				logger.info("same asset pk was removed with pk: " + asset.getAssetPk() +
														" and id: " + asset.getId() +
														" and version: " + asset.getVersion());
				break;
			}
			if ((suggestedAssetsList.get(i).getId().equalsIgnoreCase(asset.getId())) &&
					(suggestedAssetsList.get(i).getVersion().equalsIgnoreCase(asset.getVersion()))) {
				suggestedAssetsList.remove(i);
				logger.info("same asset was removed with id: " + asset.getId() +
									" and version: " + asset.getVersion());
				break;
			}
		}
		/* remove all suggested assets already inserted (id+version) */
		for (int i = suggestedAssetsList.size()-1; i >= 0; i--) {
			for (RelatedAsset relatedAsset : asset.getRelatedAssets()) {
				if ((suggestedAssetsList.get(i).getId().equalsIgnoreCase(relatedAsset.getId())) &&
						(suggestedAssetsList.get(i).getVersion().equalsIgnoreCase(relatedAsset.getVersion()))) {
					suggestedAssetsList.remove(i);
					logger.info("already inserted asset was removed with id: " + relatedAsset.getId() +
										" and version: " + relatedAsset.getVersion());
					break;
				}
			}
		}
	}
	
	public void clearSuggestedAssetsList() {
		suggestedAssetsList = new ArrayList<AssetSolrDTO>();
	}
	
	private List<AssetSolrDTO> advancedSearchQuery(Asset asset) {
		return advancedSearchQuery(asset, 0, SolrServerUtil.DEFAULT_ROWS_SIZE);
	}
	
	/** @throws IllegalArgumentException */
	private List<AssetSolrDTO> advancedSearchQuery(Asset asset, int startRow, int rowsSize) {
		String advancedQuery = SolrConversionUtil.fromAssetToQuery(asset);
		if (advancedQuery.trim().isEmpty()) {
			throw new IllegalArgumentException("There are not search terms.");
		}
		searchTime = System.currentTimeMillis();
		List<AssetSolrDTO> assetSolrList =  this.queryDismax(advancedQuery, startRow, rowsSize);
		searchTime = System.currentTimeMillis() - searchTime;
		return assetSolrList;
	}
	
	public synchronized Long getPublishedAssetsNumber() {
		if (publishedAssetsNumber == null) {
			refreshPublishedAssetsNumber();
		}
		return publishedAssetsNumber;
	}
	
	public void refreshPublishedAssetsNumber() {
		SolrDocumentList sdl = SolrServerUtil.getInstance().queryDismax("*", 0, SearchOrder.NO_ORDER);
		this.publishedAssetsNumber = sdl.getNumFound();
	}

	public synchronized List<AssetSolrDTO> getBestScoredAssetsList() {
		if (bestScoredAssetsList == null) {
			this.refreshBestScoredAssetsList();
		}
		return bestScoredAssetsList;
	}
	
	public void refreshBestScoredAssetsList() {
		SolrDocumentList sdl = SolrServerUtil.getInstance().queryDismax("*", NUMBER_ASSETS_LIST, SearchOrder.BY_BEST_RATED);
		bestScoredAssetsList = SolrConversionUtil.fromSolrDocumentListToAsset(sdl);
		/* remove invalid values */
		for (int i = bestScoredAssetsList.size()-1; i >=0 ; i--) {
			if (bestScoredAssetsList.get(i).getAverageScore() == null ||
					bestScoredAssetsList.get(i).getAverageScore() <= 0) {
				bestScoredAssetsList.remove(i);
			}
		}
		logger.info("refreshBestScoredAssetsList()");
	}
	
	public synchronized List<AssetSolrDTO> getMostReusedAssetsList() {
		if (mostReusedAssetsList == null) {
			this.refreshMostReusedAssetsList();
		}
		return mostReusedAssetsList;
	}

	public void refreshMostReusedAssetsList() {
		SolrDocumentList sdl = SolrServerUtil.getInstance().queryDismax("*", NUMBER_ASSETS_LIST, SearchOrder.BY_MOST_REUSED);
		mostReusedAssetsList = SolrConversionUtil.fromSolrDocumentListToAsset(sdl);
		/* remove invalid values */
		for (int i = mostReusedAssetsList.size()-1; i >=0 ; i--) {
			if (mostReusedAssetsList.get(i).getReuseCounter() == null ||
					mostReusedAssetsList.get(i).getReuseCounter() <= 0) {
				mostReusedAssetsList.remove(i);
			}
		}
		logger.info("refreshMostReusedAssetsList()");
	}
	
	public synchronized List<AssetSolrDTO> getLastCertifiedAssetsList() {
		if (lastCertifiedAssetsList == null) {
			this.refreshLastCertifiedAssetsList();
		}
		return lastCertifiedAssetsList;
	}

	public void refreshLastCertifiedAssetsList() {
		Asset asset = new Asset();
		asset.setState(TypesDAO.getInstance().getCertifiedAssetStateType());
		String certifiedAssetsQuery = SolrConversionUtil.fromAssetToQuery(asset);
		SolrDocumentList sdl = SolrServerUtil.getInstance().queryDismax(certifiedAssetsQuery, NUMBER_ASSETS_LIST, SearchOrder.BY_LAST_CERTIFIED);
		lastCertifiedAssetsList = SolrConversionUtil.fromSolrDocumentListToAsset(sdl);
		logger.info("refreshLastCertifiedAssetsList()");
	}
	
	public synchronized List<AssetSolrDTO> getLastPublishedAssetsList() {
		if (lastPublishedAssetsList == null) {
			this.refreshLastPublishedAssetsList();
		}
		return lastPublishedAssetsList;
	}

	public void refreshLastPublishedAssetsList() {
		SolrDocumentList sdl = SolrServerUtil.getInstance().queryDismax("*", NUMBER_ASSETS_LIST, SearchOrder.BY_LAST_PUBLISHED);
		lastPublishedAssetsList = SolrConversionUtil.fromSolrDocumentListToAsset(sdl);
		logger.info("refreshLastPublishedAssetsList()");
	}
	
	protected String clearResults() {
		this.assetResultList = new ArrayList<AssetSolrDTO>();
		return "";
	}
	
	protected List<AssetSolrDTO> queryDismax(String q, int startRow, int rowsSize) {
		SolrDocumentList sdl = SolrServerUtil.getInstance().queryDismax(q, startRow, rowsSize, this.searchOrder);
		this.numFound = sdl.getNumFound();
		if (startRow == 0) {
			/* Store the maxScore for the another pages starring */
			this.maxScore = sdl.getMaxScore();
		}
		return SolrConversionUtil.fromSolrDocumentListToAsset(sdl, this.maxScore);
	}
	
	public String getSearchTime() {
		NumberFormat nFBR = NumberFormat.getInstance();
		nFBR.setMinimumFractionDigits(3);
		nFBR.setMaximumFractionDigits(3);
		return nFBR.format(this.searchTime / 1000d);
	}
	
	/* PAGINATION */
	
	public Boolean getCanNextPage() {
		return (this.indexCurrentStart + this.pageSize ) < this.numFound;
	}
	
	public Boolean getCanPreviousPage() {
		return this.indexCurrentStart > 0;
	}
	
	public String getPageResultsMessage() {
		if (assetResultList.isEmpty()) {
			return "Your search did not match any results.";
		} else {
			int currentPage = (this.indexCurrentStart / this.pageSize) + 1;
			return "Page " + currentPage + " of " +
						this.numFound + " results (" +
						getSearchTime() + " seconds)";
		}
	}
	
	public String getResultsMessage() {
		if (assetResultList.isEmpty()) {
			return "Your search did not match any results.";
		} else {
			return assetResultList.size() + " results (" + getSearchTime() + " seconds)";			
		}
	}
	
	public void saveUserQuery(String userQuery) {
		UserQueryLogDTO userQueryDTO = new UserQueryLogDTO();
		userQueryDTO.setQueryText(userQuery);
		userQueryDTO.setIp(JSFUtil.getCurrentIP());
		userQueryDTO.setDate(Calendar.getInstance());
		if (JSFUtil.isLoggedUser()) {
			userQueryDTO.setUsername(JSFUtil.getLoggedUserDTO().getUsername());
		}
		GenericDAO.getInstance().insert(userQueryDTO);
	}
	
	public List<AssetSolrDTO> getAssetResultList() {
		return assetResultList;
	}

	public void setAssetResultList(List<AssetSolrDTO> assetResultList) {
		this.assetResultList = assetResultList;
	}
	
	public List<AssetSolrDTO> getSuggestedAssetsList() {
		return suggestedAssetsList;
	}

	public void setSuggestedAssetsList(List<AssetSolrDTO> suggestedAssetsList) {
		this.suggestedAssetsList = suggestedAssetsList;
	}
	
	public SearchOrder getSearchOrder() {
		return searchOrder;
	}

	public void setSearchOrder(SearchOrder searchOrder) {
		this.searchOrder = searchOrder;
	}
	
}
