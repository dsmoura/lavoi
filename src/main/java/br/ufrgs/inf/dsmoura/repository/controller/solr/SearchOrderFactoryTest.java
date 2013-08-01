package br.ufrgs.inf.dsmoura.repository.controller.solr;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.junit.Test;

public class SearchOrderFactoryTest {

	@Test
	public void testCreateOrderByRelevance() {
		String expected = SolrField.SCORE.getName();
		SearchOrder searchOrder = SearchOrder.BY_RELEVANCE;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Relevance has the Score as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderByName() {
		String expected = SolrField.NAME.getName();
		SearchOrder searchOrder = SearchOrder.BY_NAME;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Name has the Name as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderById() {
		String expected = SolrField.ID.getName();
		SearchOrder searchOrder = SearchOrder.BY_ID;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Id has the Id as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderByBestScore() {
		String expected = SolrField.AVERAGE_SCORE.getName();
		SearchOrder searchOrder = SearchOrder.BY_BEST_RATED;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Best Score has the Average Score as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderByReused() {
		String expected = SolrField.REUSE_COUNTER.getName();
		SearchOrder searchOrder = SearchOrder.BY_MOST_REUSED;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Top Reused has the Reuse Counter as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderByLastCertified() {
		String expected = SolrField.CERTIFICATION_DATE.getName();
		SearchOrder searchOrder = SearchOrder.BY_LAST_CERTIFIED;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Last Certified has the Certification Date as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderByLastPublished() {
		String expected = SolrField.DATE.getName();
		SearchOrder searchOrder = SearchOrder.BY_LAST_PUBLISHED;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		String firstField = ((SolrField)orders.keySet().toArray()[0]).getName();
		assertEquals("Order by Top Reused has the Reuse Counter as main field", expected, firstField);
	}
	
	@Test
	public void testCreateOrderByNoOrder() {
		SearchOrder searchOrder = SearchOrder.NO_ORDER;
		LinkedHashMap<SolrField,ORDER> orders = SearchOrderFactory.createOrder(searchOrder);
		assertEquals("Order by without order has no fields", 0, orders.keySet().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateOrderWithoutOrder() {
		SearchOrder searchOrder = null;
		SearchOrderFactory.createOrder(searchOrder);
	}

}
