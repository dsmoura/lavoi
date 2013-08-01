package br.ufrgs.inf.dsmoura.repository.controller;

public enum SystemPropertyEnum {
	
	FEEDBACK_EMAIL("feedback_email"),
	
	REPOSITORY_SUBTITLE("repository_subtitle"),
	
	EMAIL_DOMAIN_RESTRICTION("email_domain_restriction"),
	
	HOME_PAGE_URL("home_page_url"),
	REPOSITORY_URL("repository_url"),
	
	REPOSITORY_EMAIL("repository_email"),
	REPOSITORY_EMAIL_USER("repository_email_user"),
	REPOSITORY_EMAIL_PASSWORD("repository_email_password"),
	
	SMTP_HOSTNAME("smtp_hostname"),
	SMTP_SSL("smtp_ssl"),
	
	SOLR_SERVER_URL("solr_server_url"),
	
	USER_AUTHENTICATION_MODE("user_authentication_mode"),
	USER_AUTHENTICATION_DOMAIN_PREFIX("user_authentication_domain_prefix")
	
	;
	
	private String key;
	
	SystemPropertyEnum(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
}

