package br.ufrgs.inf.dsmoura.repository.controller.solr;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import br.ufrgs.inf.dsmoura.repository.controller.SystemPropertyEnum;
import br.ufrgs.inf.dsmoura.repository.model.dao.AssetDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;

public class SolrServerUtil {

	public static final int DEFAULT_ROWS_SIZE = 50;

	private final Log logger = LogFactory.getLog(getClass());
	
	private CommonsHttpSolrServer server;
	private static SolrServerUtil instance;
	
	public synchronized static SolrServerUtil getInstance() {
		if (instance == null) {
			instance = new SolrServerUtil();
			try {
				instance.createServerConnection();
			} catch(RuntimeException re) {
				instance = null;
				throw re;
			}
		}
		return instance;
	}
	
	public static void setInstance(SolrServerUtil instance) {
		SolrServerUtil.instance = instance;
	}
	
	private SolrServerUtil() {
	}
	
	private void createServerConnection() {
		final String solrURL = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.SOLR_SERVER_URL);
		try {
			server = new CommonsHttpSolrServer(solrURL);
//			server.ping();
		} catch (Exception e) {
			logger.info("solr is NOT running on " + SystemPropertyEnum.SOLR_SERVER_URL);
			throw new RuntimeException("solr is NOT running on " + server.getBaseURL(), e);
		}
		logger.info("solr running ok on " + server.getBaseURL());
	}
	
	public SolrDocumentList queryDismax(String query, int rowsSize, SearchOrder searchOrder) {
		return queryDismax(query, 0, rowsSize, searchOrder);
	}
	
	public SolrDocumentList queryDismax(String query, int startRow, int rowsSize, SearchOrder searchOrder) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setParam("defType", "edismax");
		solrQuery.setQuery(query.toLowerCase());
		solrQuery.setIncludeScore(true);

		/* pagination */
		solrQuery.setStart(startRow);
		solrQuery.setRows(rowsSize);
		
		/* ordering */
		if (searchOrder != null) {
			LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
			for (SolrField field : orders.keySet()) {
				solrQuery.addSortField(field.getName(), orders.get(field));
			}
		}
		
		/* boosting */
		String fields = "";
		for (SolrField solrField : SolrField.values()) {
			if (solrField.getBoost() != SolrFieldBoost.DEFAULT_BOOST) {
				fields += solrField.getName() + "^" + solrField.getBoost() + " ";
			}
			else {
				fields += solrField.getName() + " ";
			}
		}
		solrQuery.setParam("qf", fields);
		
		/* highlighting */
		solrQuery.setHighlight(true);
		solrQuery.setParam("hl.fl", "*");
		
		/* search */
		QueryResponse qr = null;
		try {
			qr = server.query(solrQuery, METHOD.POST);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
		SolrDocumentList sdl = qr.getResults();
		
		if (logger.isInfoEnabled()) {
			logger.info("query = " + query.toLowerCase());
			logger.info("query url : " + solrQuery.toString());
			logger.info("Found: " + sdl.getNumFound() +
							"  Start: " + sdl.getStart() +
							"  Max Score: " + sdl.getMaxScore());
			
	   		logger.info("DOCUMENTS:");
	   		for (SolrDocument sd : sdl) {
	   			logger.info("Doc: " + sd);
	   		}
	   		
	   		logger.info("HIGHLIGHTING:");
	   		for (String key : qr.getHighlighting().keySet()) {
	   			logger.info("key: " + key + " -> " + qr.getHighlighting().get(key));
	   		}
		}

		return sdl;
	}
	
	/* q=*:*&facet=true&facet.field=tag&facet.minCount=1&facet.limit=50 */
	public List<FacetField.Count> queryTags(int number, SolrField solrFieldFacet) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setFacetLimit(number);
		solrQuery.addFacetField(solrFieldFacet.getName());
		logger.info("query = " + solrQuery.toString());
		try {
			QueryResponse qr = server.query(solrQuery, METHOD.POST);
			List<FacetField> facets = qr.getFacetFields();
			return facets.get(0).getValues();
		}
		catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
	}
	
	void deleteAll() {
		this.deleteByQuery("*:*");
		logger.info("all deleted");
	}
	
	public void deleteByQuery(String query) {
		try {
			server.deleteByQuery(query);
			server.commit();
			logger.info("deleting by query = " + query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void add(SolrInputDocument doc) {
		try {
			server.add(doc);
			server.commit();
			logger.info("doc inserted with name = " + doc.getFieldValue(SolrField.NAME.getName()));
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAsset(Asset asset) {
		/* Convert the asset do solr document */
		SolrInputDocument doc = SolrConversionUtil.fromAssetToSolrInputDocument(asset);
		/* Boost the document */
		doc.setDocumentBoost(AssetDAO.getInstance().getBoost(asset));
		logger.info("boost = " + doc.getDocumentBoost() + " on asset id = " + asset.getId());
		/* Index the asset */
		SolrServerUtil.getInstance().add(doc);
	}
	
//	public List<AssetSolrDTO> query(String query) {
//		SolrQuery solrQuery = new SolrQuery();
//		solrQuery.setQuery(query.toLowerCase());
//		// solrQuery.setFacet(true);
//		// solrQuery.setSortField("", ORDER.asc);
//
//		solrQuery.setIncludeScore(true);
//
//		logger.info("query = " + solrQuery.toString());
//
//		try {
//			QueryResponse qr = server.query(solrQuery);
//			SolrDocumentList sdl = qr.getResults();
//			System.out.println("Found: " + sdl.getNumFound());
//			System.out.println("Start: " + sdl.getStart());
//			System.out.println("Max Score: " + sdl.getMaxScore());
//
//			return SolrConversionUtil.fromSolrDocumentListToAsset(sdl);
//		} catch (SolrServerException e) {
//			throw new RuntimeException(e);
//		}
//	}

}

