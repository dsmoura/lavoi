package br.ufrgs.inf.dsmoura.repository.controller.solr;

import java.util.LinkedHashMap;

import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class SearchOrderFactory {
	
	public static LinkedHashMap<SolrField,ORDER> createOrder(SearchOrder searchOrder) {
		LinkedHashMap<SolrField,ORDER> orders = new LinkedHashMap<SolrField, ORDER>();
		
		if (searchOrder == SearchOrder.BY_RELEVANCE) {
			orders.put(SolrField.SCORE, ORDER.desc);
			orders.put(SolrField.AVERAGE_SCORE, ORDER.desc);
			orders.put(SolrField.REUSE_COUNTER, ORDER.desc);
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.VERSION, ORDER.asc);
			orders.put(SolrField.DATE, ORDER.asc);
		}
		else if (searchOrder == SearchOrder.BY_NAME) {
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.SCORE, ORDER.desc);
			orders.put(SolrField.AVERAGE_SCORE, ORDER.desc);
			orders.put(SolrField.REUSE_COUNTER, ORDER.desc);
			orders.put(SolrField.VERSION, ORDER.desc);
			orders.put(SolrField.DATE, ORDER.asc);
		}
		else if (searchOrder == SearchOrder.BY_ID) {
			orders.put(SolrField.ID, ORDER.asc);
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.VERSION, ORDER.desc);
			orders.put(SolrField.DATE, ORDER.asc);
		}
		else if (searchOrder == SearchOrder.BY_BEST_RATED) {
			orders.put(SolrField.AVERAGE_SCORE, ORDER.desc);
			orders.put(SolrField.REUSE_COUNTER, ORDER.desc);
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.VERSION, ORDER.desc);
		}
		else if (searchOrder == SearchOrder.BY_MOST_REUSED) {
			orders.put(SolrField.REUSE_COUNTER, ORDER.desc);
			orders.put(SolrField.AVERAGE_SCORE, ORDER.desc);
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.VERSION, ORDER.desc);
		}
		else if (searchOrder == SearchOrder.BY_LAST_CERTIFIED) {
			orders.put(SolrField.CERTIFICATION_DATE, ORDER.desc);
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.VERSION, ORDER.desc);
		}
		else if (searchOrder == SearchOrder.BY_LAST_PUBLISHED) {
			orders.put(SolrField.DATE, ORDER.desc);
			orders.put(SolrField.NAME, ORDER.asc);
			orders.put(SolrField.VERSION, ORDER.desc);
		}
		else if (searchOrder == SearchOrder.NO_ORDER) {
			//empty list
		}
		else {
			throw new IllegalArgumentException("search order not implemented: " + searchOrder);
		}
		
		return orders;
	}

}
