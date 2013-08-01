package br.ufrgs.inf.dsmoura.repository.controller.solr;

public interface SolrFieldBoost {
	public static float DEFAULT_BOOST = 1f;
	public static float CERTIFIED_BOOST = 5f;
	public static float DISCONTINUED_BOOST = 0.5f;
	
	public static float ID_BOOST = 20f;
	public static float NAME_BOOST = 15f;
	public static float VERY_HIGH_BOOST = 5f;
	public static float HIGH_BOOST = 3f;
	public static float MEDIUM_BOOST = 2f;
	public static float LOW_BOOST = 0.5f;
}
