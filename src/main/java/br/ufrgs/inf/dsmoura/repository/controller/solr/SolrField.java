package br.ufrgs.inf.dsmoura.repository.controller.solr;

public enum SolrField {
	
	ASSET_PK("asset_pk"),
		ID("id", SolrFieldBoost.ID_BOOST),
		NAME("name", SolrFieldBoost.NAME_BOOST),
		TYPE("type", SolrFieldBoost.MEDIUM_BOOST),
		OTHER_TYPE("other_type", SolrFieldBoost.MEDIUM_BOOST),
		STATE("state"),
		DATE("date"),
		CREATION_DATE("creation_date"),
		VERSION("version"),
		SOFTWARE_LICENSE_ASSET("software_license_asset"),
		OTHER_SOFTWARE_LICENSE_ASSET("other_software_license_asset"),
		SHORT_DESCRIPTION("short_description", SolrFieldBoost.MEDIUM_BOOST),
		DESCRIPTION("description", SolrFieldBoost.MEDIUM_BOOST),
		
		USAGE_DESCRIPTION("usage_description", SolrFieldBoost.MEDIUM_BOOST),
		
		AUTHOR_USERNAME("author_username", SolrFieldBoost.HIGH_BOOST),
		AUTHOR_NAME("author_name", SolrFieldBoost.HIGH_BOOST),
		
		CREATOR_NAME("creator_name", SolrFieldBoost.HIGH_BOOST),
		
		CERTIFIER_USERNAME("certifier_username"),
		CERTIFIER_NAME("certifier_name"),
		
		CERTIFICATION_DATE("certification_date"),
		
		CONSUMER_USERNAME("consumer_username", SolrFieldBoost.MEDIUM_BOOST),
		CONSUMER_NAME("consumer_name", SolrFieldBoost.MEDIUM_BOOST),
		
		ADJUSTMENT_DESCRIPTION("adjustment_description", SolrFieldBoost.LOW_BOOST),
		
		USER_COMMENT("user_comment", SolrFieldBoost.LOW_BOOST),
		USER_COMMENT_USERNAME("user_comment_username", SolrFieldBoost.MEDIUM_BOOST),
		USER_COMMENT_USER_NAME("user_comment_user_name", SolrFieldBoost.MEDIUM_BOOST),
		
		SCORE("score"),
		
		AVERAGE_SCORE("average_score"),
		REUSE_COUNTER("reuse_counter"),
		
		APPLICATION_DOMAIN("application_domain", SolrFieldBoost.HIGH_BOOST),
		OTHER_APPLICATION_DOMAIN("other_application_domain", SolrFieldBoost.HIGH_BOOST),
		APPLICATION_SUBDOMAIN("application_subdomain", SolrFieldBoost.HIGH_BOOST),
		OTHER_APPLICATION_SUBDOMAIN("other_application_subdomain", SolrFieldBoost.HIGH_BOOST),
		ORGANIZATION("organization", SolrFieldBoost.MEDIUM_BOOST),
		PROJECT("project", SolrFieldBoost.HIGH_BOOST),
		TAG("tag", SolrFieldBoost.VERY_HIGH_BOOST),
		
		FUNCTIONAL_REQUIREMENT_TYPE("functional_requirement_type", SolrFieldBoost.MEDIUM_BOOST),
		
		NON_FUNCTIONAL_REQUIREMENT_TYPE("non_functional_requirement_type", SolrFieldBoost.MEDIUM_BOOST),
		
		LANGUAGE("language", SolrFieldBoost.MEDIUM_BOOST),
		OPERATIONAL_SYSTEM("operational_system", SolrFieldBoost.MEDIUM_BOOST),
		
		DESCRIPTOR_GROUP("descriptor_group", SolrFieldBoost.HIGH_BOOST),
		DESCRIPTOR_NAME("descriptor_name", SolrFieldBoost.HIGH_BOOST),
		DESCRIPTOR_VALUE("descriptor_value"),

		INTERFACE_SPEC("interface_spec", SolrFieldBoost.HIGH_BOOST),
		OPERATION_NAME("operation_name"),
		OPERATION_DESCRIPTION("operation_description"),
		
		DESIGN_PATTERN("design_pattern", SolrFieldBoost.MEDIUM_BOOST),
		PROGRAMMING_LANGUAGE("programming_language", SolrFieldBoost.HIGH_BOOST),
		OTHER_PROGRAMMING_LANGUAGE("other_programming_language", SolrFieldBoost.HIGH_BOOST),
		
		SOFTWARE_LICENSE("software_license", SolrFieldBoost.MEDIUM_BOOST),
		REFERENCE("reference"),
		
		ARTIFACT_NAME("artifact_name", SolrFieldBoost.MEDIUM_BOOST),
		ARTIFACT_DESCRIPTION("artifact_description"),
		ARTIFACT_TEXT("artifact_text", SolrFieldBoost.LOW_BOOST),
		
		SOURCE_CODE_TYPE("source_code_type", SolrFieldBoost.MEDIUM_BOOST),
					
		USER_INTERFACE_TYPE("user_interface_type", SolrFieldBoost.MEDIUM_BOOST),
		
		TEST_TYPE("test_type", SolrFieldBoost.MEDIUM_BOOST),
		TEST_METHOD_TYPE("test_method_type", SolrFieldBoost.MEDIUM_BOOST),
		
		RELATED_ASSET_ID("related_asset_id", SolrFieldBoost.MEDIUM_BOOST),
		RELATED_ASSET_NAME("related_asset_name", SolrFieldBoost.MEDIUM_BOOST),	
		RELATED_ASSET_VERSION("related_asset_version"),
		RELATED_ASSET_TYPE("related_asset_type"),
		
	;

	private String name;
	private float boost;
	
	SolrField(String label) {
		this(label, SolrFieldBoost.DEFAULT_BOOST);
	}
	SolrField(String label, float boost) {
		this.setName(label);
		this.setBoost(boost);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getBoost() {
		return boost;
	}
	public void setBoost(float boost) {
		this.boost = boost;
	}
	
	public static SolrField getFieldByName(String name) {
		for (SolrField sf : SolrField.values()) {
			if (sf.getName().equals(name)) {
				return sf;
			}
		}
		throw new IllegalArgumentException("Name not found: " + name);
	}
}
