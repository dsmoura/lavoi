package br.ufrgs.inf.dsmoura.repository.controller.asset;

public enum ArtifactType {
	GENERAL("General Artifact", "artifact"),
	FUNCTIONAL_REQ("Functional Requirement Artifact", "functional_requirement"),
	NON_FUNCTIONAL_REQ("Non Functional Requirement Artifact", "non_functional_requirement"),
	SOURCE_CODE("Source Code Artifact", "source_code"),
	USER_INTERFACE("User Interface Artifact", "user_interface"),
	USE_CASE("Use Case Artifact", "use_case"),
	WSDL("WSDL Artifact", "wsdl");
	
	private String name;
	private String xmlName;
	
	ArtifactType(String name, String xmlName) {
		this.name = name;
		this.xmlName = xmlName;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXmlName() {
		return xmlName;
	}
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
}
