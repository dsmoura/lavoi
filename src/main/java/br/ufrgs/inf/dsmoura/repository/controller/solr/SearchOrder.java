package br.ufrgs.inf.dsmoura.repository.controller.solr;

public enum SearchOrder {
	BY_RELEVANCE("By Relevance"),
	BY_NAME("By Name"),
	BY_ID("By ID"),
	BY_LAST_PUBLISHED("By Last Published"),
	BY_LAST_CERTIFIED("By Last Certified"),
	BY_MOST_REUSED("By Most Reused"),
	BY_BEST_RATED("By Best Rated"),
	NO_ORDER("No Order");
	
	private String name;
	
	SearchOrder(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
